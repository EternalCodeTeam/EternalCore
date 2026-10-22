package com.eternalcode.core.feature.punishment.mute;

import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

public record MuteImpl(
    UUID id,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements Mute {

    public MuteImpl {
        ExpirablePunishment.validate(createdAt, expiresAt, revocation);
    }

    public static MuteImpl issue(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        return new MuteImpl(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
