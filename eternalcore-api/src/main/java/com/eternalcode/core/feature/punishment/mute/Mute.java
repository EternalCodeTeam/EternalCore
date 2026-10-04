package com.eternalcode.core.feature.punishment.mute;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

/**
 * Immutable mute.
 *
 * @param expiresAt  null = permanent
 * @param revocation null = not revoked
 */
public record Mute(
    UUID id,
    PunishmentTarget target,
    PunishmentTarget operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements Punishment {

    public Mute {
        Punishment.validate(createdAt, expiresAt, revocation);
    }

    public static Mute issue(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        return new Mute(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
