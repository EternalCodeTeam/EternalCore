package com.eternalcode.core.user;

import com.eternalcode.core.viewer.Viewer;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class User implements Viewer {


    private final String name;
    private final UUID uuid;
    private final Instant created;
    private final Instant lastLogin;

    public User(UUID uuid, String name, Instant created, Instant lastLogin) {
        this.name = name;
        this.uuid = uuid;
        this.created = created;
        this.lastLogin = lastLogin;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getLastLogin() {
        return lastLogin;
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

