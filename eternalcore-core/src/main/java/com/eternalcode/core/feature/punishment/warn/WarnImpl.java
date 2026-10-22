package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

public record WarnImpl(
    UUID id,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements Warn {

    public WarnImpl {
        ExpirablePunishment.validate(createdAt, expiresAt, revocation);
    }

    public static WarnImpl issue(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        return new WarnImpl(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
