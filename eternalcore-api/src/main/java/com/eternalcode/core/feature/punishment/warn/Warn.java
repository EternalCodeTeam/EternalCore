package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.core.feature.punishment.RevocablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;
import java.time.Instant;
import java.util.UUID;

/**
 * Warn of a single player. Created only by {@link WarnService}.
 */
public record Warn(
    UUID id,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements RevocablePunishment {

    public Warn {
        RevocablePunishment.validate(createdAt, expiresAt, revocation);
    }

    public static Warn issue(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        return new Warn(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
