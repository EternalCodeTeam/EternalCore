/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.user;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private final Map<UUID, User> usersByUUID = new ConcurrentHashMap<>();
    private final Map<String, User> usersByName = new ConcurrentHashMap<>();

    public Set<User> getUsers() {
        return new HashSet<>(usersByUUID.values());
    }

    public Option<User> getUser(UUID uuid) {
        return Option.of(usersByUUID.get(uuid));
    }

    public Option<User> getUser(String name) {
        return Option.of(usersByName.get(name));
    }

    @Deprecated
    public Option<User> getUser(OfflinePlayer player) {
        return getUser(player.getUniqueId());
    }

    public User getOrCreate(UUID uuid, String name) {
        return create(uuid, name).orElseGet(usersByUUID.get(uuid));
    }

    @Deprecated
    public User getOrCreate(Player player) {
        return getOrCreate(player.getUniqueId(), player.getName());
    }

    public Option<User> create(UUID uuid, String name) {
        if (usersByUUID.containsKey(uuid) || usersByName.containsKey(name)) {
            return Option.none();
        }

        User user = new User(uuid, name);
        usersByUUID.put(uuid, user);
        usersByName.put(name, user);

        return Option.of(user);
    }

    public Collection<User> getUsersByUUID() {
        return Collections.unmodifiableCollection(usersByUUID.values());
    }

}
