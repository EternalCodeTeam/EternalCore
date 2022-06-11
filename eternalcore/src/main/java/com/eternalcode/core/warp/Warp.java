package com.eternalcode.core.warp;

import org.bukkit.Location;

public class Warp {

    private final String name;
    private final Location location;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location.clone();
    }

}
