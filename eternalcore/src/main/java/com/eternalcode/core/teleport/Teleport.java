package com.eternalcode.core.teleport;

import org.bukkit.Location;

import java.util.UUID;

public class Teleport {

    private final UUID uuid;
    private final Location startLocation;
    private final Location destinationLocation;
    private final long time;

    public Teleport(UUID uuid, Location startLocation, Location destinationLocation, int seconds) {
        this.uuid = uuid;
        this.startLocation = startLocation;
        this.destinationLocation = destinationLocation;
        this.time = System.currentTimeMillis() + 1000L * seconds;
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

    public long getTime() {
        return this.time;
    }

}
