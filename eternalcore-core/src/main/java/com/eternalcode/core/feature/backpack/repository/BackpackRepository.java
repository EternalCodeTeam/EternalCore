package com.eternalcode.core.feature.backpack.repository;

import com.eternalcode.core.feature.backpack.Backpack;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BackpackRepository {

    CompletableFuture<Optional<Backpack>> getBackpack(UUID ownerUniqueId);

    CompletableFuture<Optional<Backpack>> getBackpack(UUID ownerUniqueId, int backpackSlot);

    CompletableFuture<Backpack> saveBackpack(UUID ownerUniqueId, Backpack backpack);

    CompletableFuture<Integer> removeBackpacks(UUID ownerUniqueId);

    CompletableFuture<Integer> removeBackpack(UUID ownerUniqueId, int backpackSlot);
}
