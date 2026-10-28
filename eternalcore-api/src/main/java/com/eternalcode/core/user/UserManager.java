package com.eternalcode.core.user;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserManager {

    /**
     * Returns the cached user with the given unique id.
     */
    Optional<User> getUser(UUID uniqueId);

    /**
     * Returns the cached user with the given name.
     */
    Optional<User> getUser(String name);

    /**
     * Loads the user from cache or database, creating a new one if it does not exist.
     * Refreshes the user's name and last seen time.
     */
    CompletableFuture<User> findOrCreate(UUID uniqueId, String name);

    /**
     * Returns an immutable snapshot of all cached users.
     */
    Collection<User> getUsers();
}
