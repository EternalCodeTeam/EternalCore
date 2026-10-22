package com.eternalcode.core.feature.punishment.banip;

import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

public record BanIpImpl(
    UUID id,
    String ip,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements BanIp {

    public BanIpImpl {
        ExpirablePunishment.validate(createdAt, expiresAt, revocation);
    }

    public static BanIpImpl issue(String ip, PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        return new BanIpImpl(UUID.randomUUID(), ip, target, operator, reason, Instant.now(), expiresAt, null);
    }
}
