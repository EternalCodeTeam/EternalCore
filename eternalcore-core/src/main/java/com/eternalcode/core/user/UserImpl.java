package com.eternalcode.core.user;

import java.time.Instant;
import java.util.UUID;

public final class UserImpl implements User {

    private final UUID uniqueId;
    private final String name;
    private final Instant lastSeen;
    private final Instant accountCreated;

    public UserImpl(UUID uniqueId, String name, Instant lastSeen, Instant accountCreated) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.lastSeen = lastSeen;
        this.accountCreated = accountCreated;
    }

    static UserImpl create(UUID uniqueId, String name, Instant lastSeen, Instant accountCreated) {
        return new UserImpl(uniqueId, name, lastSeen, accountCreated);
    }

    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Instant getLastSeen() {
        return this.lastSeen;
    }

    @Override
    public Instant getAccountCreated() {
        return this.accountCreated;
    }

    UserImpl withName(String name) {
        return new UserImpl(this.uniqueId, name, this.lastSeen, this.accountCreated);
    }

    UserImpl withLastSeen(Instant lastSeen) {
        return new UserImpl(this.uniqueId, this.name, lastSeen, this.accountCreated);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof UserImpl user)) {
            return false;
        }

        return this.uniqueId.equals(user.uniqueId)
            && this.name.equals(user.name)
            && this.lastSeen.equals(user.lastSeen)
            && this.accountCreated.equals(user.accountCreated);
    }
}
