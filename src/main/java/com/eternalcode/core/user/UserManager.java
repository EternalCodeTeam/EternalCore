/*
 * Copyright (c) 2022. EternalCode.pl
 */

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
        return create(uuid, name).orElseGet(this.usersByUUID.get(uuid));
    }

    public Option<User> create(UUID uuid, String name) {
        if (this.usersByUUID.containsKey(uuid) || usersByName.containsKey(name)) {
            return Option.none();
        }

        User user = new User(uuid, name);
        this.usersByUUID.put(uuid, user);
        this.usersByName.put(name, user);

        return Option.of(user);
    }

    public Collection<User> getUsersByUUID() {
        return Collections.unmodifiableCollection(usersByUUID.values());
    }

}
