package com.eternalcode.core.feature.warp.inventory;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
class WarpInventoryRepositoryImpl implements WarpInventoryRepository {

    private static final Object READ_WRITE_LOCK = new Object();

    private final WarpInventoryConfig warpInventoryConfig;
    private final ConfigurationManager configurationManager;
    private final Scheduler scheduler;

    @Inject
    WarpInventoryRepositoryImpl(
        ConfigurationManager configurationManager,
        WarpInventoryConfig warpInventoryConfig,
        Scheduler scheduler
    ) {
        this.configurationManager = configurationManager;
        this.warpInventoryConfig = warpInventoryConfig;
        this.scheduler = scheduler;
    }

    @Override
    public CompletableFuture<Void> saveWarpInventoryItem(String warpName, WarpInventoryItem item) {
        return this.transactionalRun(items -> items.put(warpName, item));
    }

    @Override
    public CompletableFuture<Void> removeWarpInventoryItem(String warpName) {
        return this.transactionalRun(items -> items.remove(warpName));
    }

    @Override
    public CompletableFuture<WarpInventoryItem> getWarpInventoryItem(String warpName) {
        return this.transactionalSupply(items -> Optional.ofNullable(items.get(warpName)).orElse(null));
    }

    @Override
    public CompletableFuture<Map<String, WarpInventoryItem>> getAllWarpInventoryItems() {
        return this.transactionalSupply(items -> new HashMap<>(items));
    }

    @Override
    public WarpInventoryConfig getConfig() {
        return this.warpInventoryConfig;
    }

    private CompletableFuture<Void> transactionalRun(Consumer<Map<String, WarpInventoryItem>> editor) {
        return this.transactionalSupply(items -> {
            editor.accept(items);
            return null;
        });
    }

    private <T> CompletableFuture<T> transactionalSupply(Function<Map<String, WarpInventoryItem>, T> editor) {
        return this.scheduler.completeAsync(() -> {
            synchronized (READ_WRITE_LOCK) {
                Map<String, WarpInventoryItem> items = new HashMap<>(this.warpInventoryConfig.getItems());
                T result = editor.apply(items);
                this.warpInventoryConfig.setItems(items);
                this.configurationManager.save(this.warpInventoryConfig);
                return result;
            }
        });
    }
}
