package com.eternalcode.core.user;

import lombok.Getter;
import org.bukkit.Location;

import java.util.UUID;

public class Home {

    @Getter private final UUID uuid;
    @Getter private final String name;
    @Getter private final Location location;

    public Home(UUID uuid, String name, Location location) {
        this.uuid = uuid;
        this.name = name;
        this.location = location;
    }
}