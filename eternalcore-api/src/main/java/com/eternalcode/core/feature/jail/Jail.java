package com.eternalcode.core.feature.jail;

import org.bukkit.Location;

public class Jail {
    private final String name;
    private final Location location;

    public Jail(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }
}
