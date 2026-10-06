package com.eternalcode.core.feature.punishment.banip;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

/**
 * Immutable ban of an IP address. {@code target} is the player the ban was issued for.
 *
 * @param expiresAt  null = permanent
 * @param revocation null = not revoked
 */
public record BanIp(
    UUID id,
    String ip,
    PunishmentTarget target,
    PunishmentTarget operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements Punishment {

    public BanIp {
        Punishment.validate(createdAt, expiresAt, revocation);
    }

    public static BanIp issue(String ip, PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        return new BanIp(UUID.randomUUID(), ip, target, operator, reason, Instant.now(), expiresAt, null);
    }
}
