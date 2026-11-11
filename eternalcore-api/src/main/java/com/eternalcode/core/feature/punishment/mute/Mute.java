package com.eternalcode.core.feature.punishment.mute;

import com.eternalcode.core.feature.punishment.RevocablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;
import java.time.Instant;
import java.util.UUID;

/**
 * Mute of a single player. Created only by {@link MuteService}.
 */
public record Mute(
    UUID id,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements RevocablePunishment {

    public Mute {
        RevocablePunishment.validate(createdAt, expiresAt, revocation);
    }

    public static Mute issue(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        return new Mute(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
