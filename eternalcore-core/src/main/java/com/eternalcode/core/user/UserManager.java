package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserManager {

    private final Map<UUID, User> usersByUUID = new ConcurrentHashMap<>();
    private final Map<String, User> usersByName = new ConcurrentHashMap<>();

    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(this.usersByUUID.get(uuid));
    }

    public Optional<User> getUser(String name) {
        return Optional.ofNullable(this.usersByName.get(name));
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

    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(this.usersByUUID.values());
    }
}
