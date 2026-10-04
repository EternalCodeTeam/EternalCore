package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

/**
 * Immutable warn.
 *
 * @param expiresAt  null = permanent
 * @param revocation null = not revoked
 */
public record Warn(
    UUID id,
    PunishmentTarget target,
    PunishmentTarget operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements Punishment {

    public Warn {
        Punishment.validate(createdAt, expiresAt, revocation);
    }

    public static Warn issue(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        return new Warn(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
