package com.eternalcode.core.teleport;

import org.bukkit.Location;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

public class Teleport {

    private final UUID uuid;
    private final Location startLocation;
    private final Location destinationLocation;
    private final Instant teleportMoment;

    public Teleport(UUID uuid, Location startLocation, Location destinationLocation, TemporalAmount time) {
        this.uuid = uuid;
        this.startLocation = startLocation;
        this.destinationLocation = destinationLocation;
        this.teleportMoment = Instant.now().plus(time);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Location getStartLocation() {
        return this.startLocation;
    }

    public Location getDestinationLocation() {
        return this.destinationLocation;
    }

    public Instant getTeleportMoment() {
        return this.teleportMoment;
    }

}
