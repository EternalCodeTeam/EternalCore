package com.eternalcode.core.user.database;

import com.eternalcode.core.user.User;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.Nullable;

public interface UserRepository {

    CompletableFuture<List<User>> getActiveUsers();

    CompletableFuture<Optional<User>> getUser(UUID uniqueId);

    CompletableFuture<Optional<User>> getUser(String name);

    CompletableFuture<User> saveUser(User user);

    CompletableFuture<User> updateUser(UUID uniqueId, String name);
}
