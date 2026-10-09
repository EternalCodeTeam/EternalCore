package com.eternalcode.core.feature.kit;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.kit.cooldown.KitCooldownStore;
import com.eternalcode.core.feature.kit.repository.KitRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
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

/**
 * Copy-on-write cache: readers never lock, writers swap an immutable snapshot.
 */
@Service
class KitServiceImpl implements KitService {

    private static final Comparator<Kit> BY_SLOT = Comparator.comparingInt(Kit::slot)
        .thenComparing(Kit::name);

    private final Object writeLock = new Object();
    private volatile Map<String, Kit> kits = Map.of();

    private final KitRepository kitRepository;
    private final KitCooldownStore cooldownStore;

    @Inject
    KitServiceImpl(KitRepository kitRepository, KitCooldownStore cooldownStore) {
        this.kitRepository = kitRepository;
        this.cooldownStore = cooldownStore;

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
            .sorted(BY_SLOT)
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
        this.cooldownStore.deleteKit(name);
    }

    @Subscribe(EternalReloadEvent.class)
    void onReload(EternalReloadEvent event) {
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
            })
            .exceptionally(FutureHandler::handleException);
    }

    private void edit(Consumer<Map<String, Kit>> editor) {
        synchronized (this.writeLock) {
            Map<String, Kit> snapshot = new HashMap<>(this.kits);
            editor.accept(snapshot);
            this.kits = Map.copyOf(snapshot);
        }
    }
}
