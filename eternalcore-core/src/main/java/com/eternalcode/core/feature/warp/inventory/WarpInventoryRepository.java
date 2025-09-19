package com.eternalcode.core.feature.warp.inventory;

import com.eternalcode.core.feature.warp.WarpInventoryItem;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface WarpInventoryRepository {

    CompletableFuture<Void> saveWarpInventoryItem(String warpName, WarpInventoryItem item);

    CompletableFuture<Void> removeWarpInventoryItem(String warpName);

    CompletableFuture<WarpInventoryItem> getWarpInventoryItem(String warpName);

    CompletableFuture<Map<String, WarpInventoryItem>> getAllWarpInventoryItems();

    WarpInventoryConfig getConfig();
}
