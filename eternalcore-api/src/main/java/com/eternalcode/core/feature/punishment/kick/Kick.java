package com.eternalcode.core.feature.punishment.kick;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentStatus;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import java.time.Instant;
import java.util.UUID;

/**
 * Kick is instantaneous: it never expires and cannot be revoked. Created only by {@link KickService}.
 */
public record Kick(
    UUID id,
    PunishmentTarget target,
    PunishmentActor operator,
    String reason,
    Instant createdAt,
    boolean massKick
) implements Punishment {

    public static Kick issue(PunishmentTarget target, PunishmentActor operator, String reason, boolean massKick) {
        return new Kick(UUID.randomUUID(), target, operator, reason, Instant.now(), massKick);
    }

    @Override
    public PunishmentStatus status() {
        return PunishmentStatus.INSTANT;
    }

}
