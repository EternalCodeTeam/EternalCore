package com.eternalcode.core.feature.punishment.kick;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentStatus;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;

import java.time.Instant;
import java.util.UUID;

/**
 * Immutable kick. Kick is instantaneous: it never expires and cannot be revoked,
 * its status is always {@link PunishmentStatus#INSTANT}.
 *
 * @param massKick true when the kick was part of /kickall
 */
public record Kick(
    UUID id,
    PunishmentTarget target,
    PunishmentTarget operator,
    String reason,
    Instant createdAt,
    boolean massKick
) implements Punishment {

    public static Kick issue(PunishmentTarget target, PunishmentTarget operator, String reason, boolean massKick) {
        return new Kick(UUID.randomUUID(), target, operator, reason, Instant.now(), massKick);
    }

    @Override
    public Instant expiresAt() {
        return null;
    }

    @Override
    public Revocation revocation() {
        return null;
    }

    @Override
    public PunishmentStatus status(Instant now) {
        return PunishmentStatus.INSTANT;
    }
}
