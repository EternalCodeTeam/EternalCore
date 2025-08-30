package com.eternalcode.core.user.database;

import com.eternalcode.core.user.User;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.Nullable;

public interface UserRepository {

    @Nullable CompletableFuture<User> getUser(UUID uniqueId);

    CompletableFuture<Void> saveUser(User player);

    CompletableFuture<User> updateUser(UUID uniqueId, User player);

    CompletableFuture<Void> deleteUser(UUID uniqueId);

    CompletableFuture<Collection<User>> fetchAllUsers();

    CompletableFuture<Collection<User>> fetchUsersBatch(int batchSize);
}
