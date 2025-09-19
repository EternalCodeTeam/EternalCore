package com.eternalcode.core.feature.warp.inventory;

import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.feature.warp.inventory.WarpInventoryConfig.BorderSection;
import com.eternalcode.core.feature.warp.inventory.WarpInventoryConfig.DecorationItemsSection;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class WarpInventoryConfigService {

    private final WarpInventoryRepository warpInventoryRepository;
    private final WarpInventoryConfig warpInventoryConfig;

    @Inject
    public WarpInventoryConfigService(
        WarpInventoryRepository warpInventoryRepository,
        WarpInventoryConfig warpInventoryConfig
    ) {
        this.warpInventoryRepository = warpInventoryRepository;
        this.warpInventoryConfig = warpInventoryConfig;
    }

    public CompletableFuture<WarpInventoryConfigData> getWarpInventoryData() {
        return this.warpInventoryRepository.getAllWarpInventoryItems()
            .thenApply(items -> {
                return new WarpInventoryConfigData(
                    this.warpInventoryConfig.display().title(),
                    this.warpInventoryConfig.border(),
                    this.warpInventoryConfig.decorationItems(),
                    items
                );
            });
    }

    public CompletableFuture<Void> addWarpItem(String warpName, WarpInventoryItem item) {
        return this.warpInventoryRepository.saveWarpInventoryItem(warpName, item);
    }

    public CompletableFuture<WarpInventoryItem> removeWarpItem(String warpName) {
        return this.warpInventoryRepository.getWarpInventoryItem(warpName)
            .thenCompose(item -> {
                if (item != null) {
                    return this.warpInventoryRepository.removeWarpInventoryItem(warpName)
                        .thenApply(v -> item);
                }
                return CompletableFuture.completedFuture(null);
            });
    }

    public CompletableFuture<Map<String, WarpInventoryItem>> getWarpItems() {
        return this.warpInventoryRepository.getAllWarpInventoryItems();
    }


    public record WarpInventoryConfigData(
        String title,
        BorderSection border,
        DecorationItemsSection decorationItems,
        Map<String, WarpInventoryItem> items
    ) {

    }
}
