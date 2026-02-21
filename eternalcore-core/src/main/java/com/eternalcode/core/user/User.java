package com.eternalcode.core.user;

import com.eternalcode.core.viewer.Viewer;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class User implements Viewer {

    private final String name;
    private final UUID uuid;
    private final Instant lastSeen;
    private final Instant accountCreated;

    public User(UUID uuid, String name, Instant lastSeen, Instant accountCreated) {
        this.name = name;
        this.uuid = uuid;
        this.lastSeen = lastSeen;
        this.accountCreated = accountCreated;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    public Instant getLastSeen() {
        return this.lastSeen;
    }

    public Instant getAccountCreated() {
        return this.accountCreated;
    }

    public User updateLastSeen(Instant lastSeen) {
        return new User(this.uuid, this.name, lastSeen, this.accountCreated);
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
        return this.name.equals(user.name) &&
                this.uuid.equals(user.uuid) &&
                Objects.equals(this.lastSeen, user.lastSeen) &&
                Objects.equals(this.accountCreated, user.accountCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.uuid, this.lastSeen, this.accountCreated);
    }
}
