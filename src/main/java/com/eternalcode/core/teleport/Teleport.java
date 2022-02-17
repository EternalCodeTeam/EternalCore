package com.eternalcode.core.teleport;

import lombok.Getter;
import org.bukkit.Location;

import java.util.UUID;

public class Teleport {

    @Getter private final UUID uuid;
    @Getter private final Location location;
    @Getter private final long time;

    public Teleport(UUID uuid, Location location, int seconds) {
        this.uuid = uuid;
        this.location = location;
        this.time = System.currentTimeMillis() + 1000L * seconds;
    }
}
