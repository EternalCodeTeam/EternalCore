package com.eternalcode.core.feature.punishment.ban;

import com.eternalcode.core.feature.punishment.RevocablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;
import java.time.Instant;
import java.util.UUID;

/**
 * Ban of a single player account. Created only by {@link BanService}.
 */
public record Ban(
    UUID id,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Revocation revocation
) implements RevocablePunishment {

    public Ban {
        RevocablePunishment.validate(createdAt, expiresAt, revocation);
    }

    public static Ban issue(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        return new Ban(UUID.randomUUID(), target, operator, reason, Instant.now(), expiresAt, null);
    }
}
