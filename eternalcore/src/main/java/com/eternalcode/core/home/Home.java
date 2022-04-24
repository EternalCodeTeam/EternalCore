package com.eternalcode.core.home;

import org.bukkit.Location;

import java.util.Objects;
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

    public Home(String name, Location location) {
        this.uuid = UUID.randomUUID();
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Home home)) {
            return false;
        }

        return this.uuid.equals(home.uuid) && this.name.equals(home.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid, this.name);
    }

}
