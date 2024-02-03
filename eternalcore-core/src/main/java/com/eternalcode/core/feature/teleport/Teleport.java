package com.eternalcode.core.feature.teleport;

import com.eternalcode.core.position.Position;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

class Teleport {

    private final UUID uuid;
    private final Position startLocation;
    private final Position destinationLocation;
    private final Instant teleportMoment;

    Teleport(UUID uuid, Position startLocation, Position destinationLocation, TemporalAmount time) {
        this.uuid = uuid;
        this.startLocation = startLocation;
        this.destinationLocation = destinationLocation;
        this.teleportMoment = Instant.now().plus(time);
    }

    UUID getUuid() {
        return this.uuid;
    }

    Position getStartLocation() {
        return this.startLocation;
    }

    Position getDestinationLocation() {
        return this.destinationLocation;
    }

    Instant getTeleportMoment() {
        return this.teleportMoment;
    }

}
