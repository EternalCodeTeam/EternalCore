package com.eternalcode.core.feature.warp.inventory;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.feature.warp.inventory.WarpInventoryConfig.BorderSection;
import com.eternalcode.core.feature.warp.inventory.WarpInventoryConfig.DecorationItemsSection;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class WarpInventoryConfigService {

    private final WarpInventoryConfig warpInventoryConfig;
    private final ConfigurationManager configurationManager;
    private final Scheduler scheduler;

    @Inject
    public WarpInventoryConfigService(
        ConfigurationManager configurationManager,
        WarpInventoryConfig warpInventoryConfig,
        Scheduler scheduler
    ) {
        this.configurationManager = configurationManager;
        this.warpInventoryConfig = warpInventoryConfig;
        this.scheduler = scheduler;
    }

    public CompletableFuture<WarpInventoryConfigData> getWarpInventoryData() {
        return this.scheduler.<Map<String, WarpInventoryItem>>completeAsync(() -> new HashMap<>(
                this.warpInventoryConfig.items()))
            .thenApply(items -> new WarpInventoryConfigData(
                this.warpInventoryConfig.display().title(),
                this.warpInventoryConfig.border(),
                this.warpInventoryConfig.decorationItems(),
                items
            ));
    }

    public CompletableFuture<Void> addWarpItem(String warpName, WarpInventoryItem item) {
        return this.scheduler.completeAsync(() -> {
            this.warpInventoryConfig.items().put(warpName, item);
            this.configurationManager.save(this.warpInventoryConfig);
            return null;
        });
    }

    public CompletableFuture<WarpInventoryItem> removeWarpItem(String warpName) {
        if (warpName == null || warpName.trim().isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }

        CompletableFuture<WarpInventoryItem> result;
        if (warpName == null || warpName.trim().isEmpty()) {
            result = CompletableFuture.completedFuture(null);
        }
        else {
            result =
                this.scheduler.completeAsync(() -> this.warpInventoryConfig.items()
                    .get(warpName));
        }

        return result
            .thenCompose(item -> {
                if (item != null) {
                    if (warpName == null || warpName.trim().isEmpty()) {
                        throw new IllegalArgumentException("Warp name cannot be null or empty");
                    }

                    return this.scheduler.<Void>completeAsync(() -> {
                            this.warpInventoryConfig.items().remove(warpName);
                            this.configurationManager.save(this.warpInventoryConfig);
                            return null;
                        })
                        .thenApply(v -> item);
                }
                return CompletableFuture.completedFuture(null);
            });
    }

    public Map<String, WarpInventoryItem> getWarpItems() {
        return new HashMap<>(this.warpInventoryConfig.items());
    }

    public record WarpInventoryConfigData(
        String title,
        BorderSection border,
        DecorationItemsSection decorationItems,
        Map<String, WarpInventoryItem> items
    ) {
    }
}
