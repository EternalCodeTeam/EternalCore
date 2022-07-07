package com.eternalcode.core.teleport;

import com.eternalcode.core.shared.Position;
import org.bukkit.Location;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

public class Teleport {

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

    public UUID getUuid() {
        return this.uuid;
    }

    public Position getStartLocation() {
        return this.startLocation;
    }

    public Position getDestinationLocation() {
        return this.destinationLocation;
    }

    public Instant getTeleportMoment() {
        return this.teleportMoment;
    }

}
