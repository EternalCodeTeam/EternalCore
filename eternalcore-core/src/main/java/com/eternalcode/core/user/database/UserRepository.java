package com.eternalcode.core.user.database;

import com.eternalcode.core.user.User;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserRepository {

    CompletableFuture<Optional<User>> getUser(UUID uniqueId);

    CompletableFuture<Optional<User>> getUser(String name);

    CompletableFuture<Void> saveUser(User user);

    CompletableFuture<Void> updateUser(User user);
}
