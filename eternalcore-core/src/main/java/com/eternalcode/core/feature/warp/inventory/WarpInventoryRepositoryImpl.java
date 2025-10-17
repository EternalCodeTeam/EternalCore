package com.eternalcode.core.feature.warp.inventory;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Repository
class WarpInventoryRepositoryImpl implements WarpInventoryRepository {

    private final WarpInventoryConfig warpInventoryConfig;
    private final ConfigurationManager configurationManager;
    private final Scheduler scheduler;

    @Inject
    public WarpInventoryRepositoryImpl(
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
        if (warpName == null || warpName.trim().isEmpty()) {
            throw new IllegalArgumentException("Warp name cannot be null or empty");
        }
        if (item == null) {
            throw new IllegalArgumentException("WarpInventoryItem cannot be null");
        }

        return this.scheduler.completeAsync(() -> {
            this.warpInventoryConfig.items().put(warpName, item);
            this.configurationManager.save(this.warpInventoryConfig);
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> removeWarpInventoryItem(String warpName) {
        if (warpName == null || warpName.trim().isEmpty()) {
            throw new IllegalArgumentException("Warp name cannot be null or empty");
        }

        return this.scheduler.completeAsync(() -> {
            this.warpInventoryConfig.items().remove(warpName);
            this.configurationManager.save(this.warpInventoryConfig);
            return null;
        });
    }

    @Override
    public CompletableFuture<WarpInventoryItem> getWarpInventoryItem(String warpName) {
        if (warpName == null || warpName.trim().isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }

        return this.scheduler.completeAsync(() -> this.warpInventoryConfig.items().get(warpName));
    }

    @Override
    public CompletableFuture<Map<String, WarpInventoryItem>> getAllWarpInventoryItems() {
        return this.scheduler.completeAsync(() -> new HashMap<>(this.warpInventoryConfig.items()));
    }
}
