package com.eternalcode.core.feature.kit.buffer;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.inventory.ItemStack;

interface KitBufferRepository {

    CompletableFuture<List<ItemStack>> find(UUID playerUniqueId);

    CompletableFuture<Void> save(UUID playerUniqueId, List<ItemStack> items);

    CompletableFuture<Void> delete(UUID playerUniqueId);

}
