package com.eternalcode.core.teleport;

import org.bukkit.Location;

import java.util.UUID;

public class Teleport {

    private final UUID uuid;
    private final Location location;
    private final long time;

    public Teleport(UUID uuid, Location location, int secounds){
        this.uuid = uuid;
        this.location = location;
        this.time = System.currentTimeMillis() + 1000L * secounds;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Location getLocation() {
        return this.location;
    }

    public long getTime() {
        return this.time;
    }
}
