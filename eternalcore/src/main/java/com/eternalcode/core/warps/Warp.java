package com.eternalcode.core.warps;

import org.bukkit.Location;

public class Warp {

    private final String name;
    private boolean enabled;
    private Location location;
    private String permission;

    public Warp(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}