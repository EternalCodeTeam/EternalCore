package com.eternalcode.core.user;

import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private final Map<UUID, User> usersByUUID = new ConcurrentHashMap<>();
    private final Map<String, User> usersByName = new ConcurrentHashMap<>();

    public Set<User> getUsers() {
        return new HashSet<>(this.usersByUUID.values());
    }

    public Option<User> getUser(UUID uuid) {
        return Option.of(this.usersByUUID.get(uuid));
    }

    public Option<User> getUser(String name) {
        return Option.of(this.usersByName.get(name));
    }

    public User getOrCreate(UUID uuid, String name) {
        User userByUUID = this.usersByUUID.get(uuid);

        if (userByUUID != null) {
            return userByUUID;
        }

        User userByName = this.usersByName.get(name);

        if (userByName != null) {
            return userByName;
        }

        return this.create(uuid, name);
    }

    public User create(UUID uuid, String name) {
        if (this.usersByUUID.containsKey(uuid) || this.usersByName.containsKey(name)) {
            throw new IllegalStateException("User already exists");
        }

        User user = new User(uuid, name);
        this.usersByUUID.put(uuid, user);
        this.usersByName.put(name, user);

        return user;
    }

    public Collection<User> getUsersByUUID() {
        return Collections.unmodifiableCollection(this.usersByUUID.values());
    }

    public Collection<User> getUsersByName() {
        return Collections.unmodifiableCollection(this.usersByName.values());
    }
}
