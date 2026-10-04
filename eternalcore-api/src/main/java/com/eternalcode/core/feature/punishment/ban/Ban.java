package com.eternalcode.core.feature.punishment.ban;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

/**
 * Immutable ban of a single player account.
 *
 * @param expiresAt  null = permanent
 * @param revocation null = not revoked
 */
public record Ban(
    UUID id,
    PunishmentTarget target,
    PunishmentTarget operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements Punishment {

    public Ban {
        Punishment.validate(createdAt, expiresAt, revocation);
    }

    public static Ban issue(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        return new Ban(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
