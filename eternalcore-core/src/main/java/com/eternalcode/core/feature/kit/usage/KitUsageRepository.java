package com.eternalcode.core.feature.kit.usage;

import com.eternalcode.core.feature.kit.KitUsage;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface KitUsageRepository {

    CompletableFuture<Map<String, KitUsage>> findUsages(UUID playerUniqueId);

    CompletableFuture<Void> save(UUID playerUniqueId, String kitName, KitUsage usage);

    CompletableFuture<Void> resetClaim(UUID playerUniqueId, String kitName);

    CompletableFuture<Void> deleteByKit(String kitName);

}
