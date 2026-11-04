package com.eternalcode.core.feature.kit.repository;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public class KitRepositoryImpl implements KitRepository {

    private static final Object READ_WRITE_LOCK = new Object();

    private final KitRepositoryConfig kitRepositoryConfig;
    private final ConfigurationManager configurationManager;
    private final Scheduler scheduler;

    @Inject
    public KitRepositoryImpl(KitRepositoryConfig kitRepositoryConfig, ConfigurationManager configurationManager, Scheduler scheduler) {
        this.kitRepositoryConfig = kitRepositoryConfig;
        this.configurationManager = configurationManager;
        this.scheduler = scheduler;
    }

    @Override
    public CompletableFuture<Void> saveKit(Kit kit) {
        KitRepositoryConfig.KitConfigEntry kitConfigEntry = new KitRepositoryConfig.KitConfigEntry(
            kit.name(),
            kit.permission(),
            kit.duration(),
            kit.inventory()
        );

        return this.transactionalRun(kits -> kits.put(kit.name(), kitConfigEntry));
    }

    @Override
    public CompletableFuture<Void> changeKitName(String oldName, String newName) {
        return this.transactionalRun(kits -> {
            KitRepositoryConfig.KitConfigEntry kitConfigEntry = kits.get(oldName);
            kitConfigEntry.name = newName;
            kits.remove(oldName);
            kits.put(newName, kitConfigEntry);
        });
    }

    @Override
    public CompletableFuture<Optional<Kit>> getKit(String kitName) {
        return this.transactionalSupply(kits -> Optional.of(kits.get(kitName).toKit()));
    }

    @Override
    public CompletableFuture<List<Kit>> getKits() {
        return this.transactionalSupply(kits -> {
            List<Kit> allKits = new ArrayList<>();

            kits.forEach((name, kitConfigEntry) -> allKits.add(kitConfigEntry.toKit()));

            return allKits;
        });
    }

    @Override
    public CompletableFuture<Void> removeKit(String kitName) {
        return this.transactionalRun(kits -> kits.remove(kitName));
    }


    private CompletableFuture<Void> transactionalRun(Consumer<Map<String, KitRepositoryConfig.KitConfigEntry>> editor) {
        return transactionalSupply(kits -> {
            editor.accept(kits);
            return null;
        });
    }

    private <T> CompletableFuture<T> transactionalSupply(Function<Map<String, KitRepositoryConfig.KitConfigEntry>, T> editor) {
        return scheduler.completeAsync(() -> {
            synchronized (READ_WRITE_LOCK) {
                Map<String, KitRepositoryConfig.KitConfigEntry> kits = new HashMap<>(this.kitRepositoryConfig.kits);
                T result = editor.apply(kits);
                this.kitRepositoryConfig.kits = kits;
                this.configurationManager.save(this.kitRepositoryConfig);
                return result;
            }
        });
    }
}
