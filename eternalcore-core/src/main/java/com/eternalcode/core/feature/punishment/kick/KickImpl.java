package com.eternalcode.core.feature.punishment.kick;

import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;

import java.time.Instant;
import java.util.UUID;

public record KickImpl(
    UUID id,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    boolean massKick
) implements Kick {

    public static KickImpl issue(PunishmentTarget target, PunishmentActor operator, String reason, boolean massKick) {
        return new KickImpl(UUID.randomUUID(), target, operator, reason, Instant.now(), massKick);
    }
}
