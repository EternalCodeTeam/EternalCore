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

    private final WarpInventoryConfig config;
    private final ConfigurationManager configurationManager;
    private final Scheduler scheduler;

    @Inject
    public WarpInventoryConfigService(
        ConfigurationManager configurationManager,
        WarpInventoryConfig config,
        Scheduler scheduler
    ) {
        this.configurationManager = configurationManager;
        this.config = config;
        this.scheduler = scheduler;
    }

    public CompletableFuture<WarpInventoryConfigData> getWarpInventoryData() {
        return scheduler.<Map<String, WarpInventoryItem>>completeAsync(() -> new HashMap<>(config.items()))
            .thenApply(items -> new WarpInventoryConfigData(
                config.display().title(),
                config.border(),
                config.decorationItems(),
                items
            ));
    }

    public CompletableFuture<Void> addWarpItem(String warpName, WarpInventoryItem item) {
        return scheduler.completeAsync(() -> {
            config.items().put(warpName, item);
            configurationManager.save(config);
            return null;
        });
    }

    public CompletableFuture<WarpInventoryItem> removeWarpItem(String warpName) {
        if (isBlank(warpName)) {
            return CompletableFuture.completedFuture(null);
        }

        return scheduler.<WarpInventoryItem>completeAsync(() -> config.items().get(warpName))
            .thenCompose(item -> {
                if (item == null) {
                    return CompletableFuture.completedFuture(null);
                }

                return scheduler.<Void>completeAsync(() -> {
                        config.items().remove(warpName);
                        configurationManager.save(config);
                        return null;
                    })
                    .thenApply(v -> item);
            });
    }

    public Map<String, WarpInventoryItem> getWarpItems() {
        return new HashMap<>(config.items());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public record WarpInventoryConfigData(
        String title,
        BorderSection border,
        DecorationItemsSection decorationItems,
        Map<String, WarpInventoryItem> items
    ) {
    }
}
