package com.eternalcode.core.user;

import org.bukkit.Location;

import java.util.UUID;

public class Home {

    private final UUID uuid;
    private final String name;
    private final Location location;

    public Home(UUID uuid, String name, Location location) {
        this.uuid = uuid;
        this.name = name;
        this.location = location;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }
}