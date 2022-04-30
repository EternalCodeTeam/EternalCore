package com.eternalcode.core.teleport;

import org.bukkit.Location;

import java.util.UUID;

public class TeleportRequest {

    private final Location destinationLocation;
    private final UUID uuid;

    public TeleportRequest(UUID uuid, Location destinationLocation) {
        this.uuid = uuid;
        this.destinationLocation = destinationLocation;
    }


    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public UUID getUuid() {
        return uuid;
    }
}
