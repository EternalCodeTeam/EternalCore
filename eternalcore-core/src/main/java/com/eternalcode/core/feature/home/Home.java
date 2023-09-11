package com.eternalcode.core.feature.home;

import org.bukkit.Location;

import java.util.Objects;
import java.util.UUID;

class Home {

    private final UUID uuid;
    private final UUID owner;
    private final String name;
    private final Location location;

    Home(UUID uuid, UUID owner, String name, Location location) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.location = location;
    }

    Home(UUID owner, String name, Location location) {
        this.owner = owner;
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.location = location;
    }

    UUID getUuid() {
        return this.uuid;
    }

    UUID getOwner() {
        return this.owner;
    }

    String getName() {
        return this.name;
    }

    Location getLocation() {
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
