package com.eternalcode.core.feature.home;

import java.util.Objects;
import java.util.UUID;

import com.eternalcode.commons.bukkit.position.Position;

public class HomeImpl implements Home {

    private final UUID uuid;
    private final UUID owner;
    private final String name;
    private final Position position;

    public HomeImpl(UUID uuid, UUID owner, String name, Position position) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.position = position;
    }

    public HomeImpl(UUID owner, String name, Position position) {
        this.owner = owner;
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.position = position;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof HomeImpl homeImpl)) {
            return false;
        }

        return this.uuid.equals(homeImpl.uuid) && this.name.equals(homeImpl.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid, this.name);
    }
}
