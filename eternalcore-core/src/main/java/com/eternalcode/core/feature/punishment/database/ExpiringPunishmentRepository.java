package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Repository of a punishment domain that can expire and be revoked (ban, ip ban, mute, warn).
 * Methods block until the database answers - call them only from an asynchronous context.
 */
public interface ExpiringPunishmentRepository<T extends ExpirablePunishment> {

    void save(T punishment);

    /**
     * @return true when the punishment was active and got revoked, false when it was already expired or revoked
     */
    boolean revoke(UUID punishmentId, PunishmentActor revokedBy, Instant revokedAt);

    List<T> findActive(UUID targetUuid);

    List<T> findAllActive();
}
