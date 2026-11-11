package com.eternalcode.core.feature.punishment;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Common read-only view of every punishment domain (ban, ip ban, mute, warn, kick).
 * Instances are created only by EternalCore services, never by API consumers.
 */
public interface Punishment {

    UUID id();

    PunishmentTarget target();

    PunishmentActor operator();

    String reason();

    Instant createdAt();

    default Duration relativeTime() {
        return Duration.between(createdAt(), Instant.now());
    }

    PunishmentStatus status();

}
