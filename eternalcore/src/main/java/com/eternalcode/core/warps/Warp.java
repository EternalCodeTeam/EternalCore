package com.eternalcode.core.warps;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public class Warp {

    @Getter private final String name;
    @Getter @Setter private boolean enabled;
    @Getter @Setter private Location location;
    @Getter @Setter private String permission;

    public Warp(String name) {
        this.name = name;
    }
}