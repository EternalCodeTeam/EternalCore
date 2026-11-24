package com.eternalcode.core.feature.kit;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.kit.repository.KitRepository;
import com.eternalcode.core.feature.kit.usage.KitUsageStore;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalReloadEvent;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class KitServiceImpl implements KitService {

    private final Object writeLock = new Object();
    private volatile Map<String, Kit> kits = Map.of();

    private final KitRepository kitRepository;
    private final KitUsageStore usageStore;
    private final Publisher publisher;

    @Inject
    KitServiceImpl(KitRepository kitRepository, KitUsageStore usageStore, Publisher publisher) {
        this.kitRepository = kitRepository;
        this.usageStore = usageStore;
        this.publisher = publisher;

        this.loadKits();
    }

    @Override
    public Optional<Kit> findKit(String name) {
        if (name == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(this.kits.get(name));
    }

    @Override
    public boolean exists(String name) {
        return this.findKit(name).isPresent();
    }

    @Override
    public List<Kit> getKits() {
        return this.kits.values().stream()
            .sorted(Comparator.comparingInt(Kit::slot).thenComparing(Kit::name))
            .toList();
    }

    @Override
    public void saveKit(Kit kit) {
        this.edit(snapshot -> snapshot.put(kit.name(), kit));

        this.kitRepository.save(kit)
            .exceptionally(FutureHandler::handleException);
    }

    @Override
    public void deleteKit(String name) {
        this.edit(snapshot -> snapshot.remove(name));

        this.kitRepository.delete(name)
            .exceptionally(FutureHandler::handleException);
        this.usageStore.deleteKit(name);
    }

    @Subscribe(EternalReloadEvent.class)
    void onReload() {
        this.loadKits();
    }

    private void loadKits() {
        this.kitRepository.findAll()
            .thenAccept(loadedKits -> {
                Map<String, Kit> loaded = loadedKits.stream()
                    .collect(Collectors.toUnmodifiableMap(Kit::name, Function.identity()));

                synchronized (this.writeLock) {
                    this.kits = loaded;
                }

                this.publisher.publish(new KitsLoadedEvent(this.getKits()));
            })
            .exceptionally(FutureHandler::handleException);
    }

    private void edit(Consumer<Map<String, Kit>> editor) {
        synchronized (this.writeLock) {
            Map<String, Kit> snapshot = new HashMap<>(this.kits);
            editor.accept(snapshot);
            this.kits = Map.copyOf(snapshot);
        }

        this.publisher.publish(new KitsLoadedEvent(this.getKits()));
    }
}
