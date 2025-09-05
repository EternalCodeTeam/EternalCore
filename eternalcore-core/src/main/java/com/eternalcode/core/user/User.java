package com.eternalcode.core.user;

import com.eternalcode.core.viewer.Viewer;

import java.util.Objects;
import java.util.UUID;

public class User implements Viewer {


    private final String name;
    private final UUID uuid;

    public User(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        return this.name.equals(user.name) && this.uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.uuid);
    }
}

