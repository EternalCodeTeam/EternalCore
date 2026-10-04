package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Repository of a punishment domain that can expire and be revoked (ban, ip ban, mute, warn).
 */
public interface ExpiringPunishmentRepository<T extends Punishment> {

    CompletableFuture<Void> save(T punishment);

    /**
     * @return true when the punishment was active and got revoked, false when it was already expired or revoked
     */
    CompletableFuture<Boolean> revoke(UUID punishmentId, PunishmentTarget revokedBy, Instant revokedAt);

    CompletableFuture<List<T>> findActive(UUID targetUuid);

    CompletableFuture<List<T>> findAllActive();
}
