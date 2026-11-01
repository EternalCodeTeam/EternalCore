package com.eternalcode.core.feature.kit.cooldown;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface KitCooldownRepository {

    CompletableFuture<Optional<Instant>> findExpiration(UUID playerUniqueId, String kitName);

    CompletableFuture<Map<String, Instant>> findExpirations(UUID playerUniqueId);

    CompletableFuture<Void> save(UUID playerUniqueId, String kitName, Instant expiresAt);

    CompletableFuture<Void> delete(UUID playerUniqueId, String kitName);

    CompletableFuture<Void> deleteByKit(String kitName);

    CompletableFuture<Void> deleteExpired(Instant now);

}
