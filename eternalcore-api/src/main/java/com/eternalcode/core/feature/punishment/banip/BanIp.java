package com.eternalcode.core.feature.punishment.banip;

import com.eternalcode.core.feature.punishment.RevocablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;
import java.time.Instant;
import java.util.UUID;

/**
 * Ban of an IP address; {@code target} is the player the ban was issued for. Created only by {@link BanIpService}.
 */
public record BanIp(
    UUID id,
    String ip,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements RevocablePunishment {

    public BanIp {
        RevocablePunishment.validate(createdAt, expiresAt, revocation);
    }

    public static BanIp issue(String ip, PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        return new BanIp(UUID.randomUUID(), ip, target, operator, reason, Instant.now(), expiresAt, null);
    }
}
