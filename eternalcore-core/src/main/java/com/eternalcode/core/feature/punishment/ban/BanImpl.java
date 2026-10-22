package com.eternalcode.core.feature.punishment.ban;

import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

public record BanImpl(
    UUID id,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements Ban {

    public BanImpl {
        ExpirablePunishment.validate(createdAt, expiresAt, revocation);
    }

    public static BanImpl issue(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        return new BanImpl(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
