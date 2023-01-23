package com.eternalcode.core.feature.home;

import org.bukkit.Location;

import java.util.Objects;
import java.util.UUID;

public class Home {

    private final UUID uuid;
    private final UUID owner;
    private final String name;
    private final Location location;

    public Home(UUID uuid, UUID owner, String name, Location location) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.location = location;
    }

    public Home(UUID owner, String name, Location location) {
        this.owner = owner;
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.location = location;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public UUID getOwner() {
        return owner;
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
