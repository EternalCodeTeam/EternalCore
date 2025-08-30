package com.eternalcode.core.user.database;

import com.eternalcode.core.user.User;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserRepository {

    CompletableFuture<User> getUser(UUID uniqueId);

    CompletableFuture<Void> saveUser(User player);

    CompletableFuture<User> updateUser(UUID uniqueId, User player);

    CompletableFuture<Void> deleteUser(UUID uniqueId);

    CompletableFuture<Collection<User>> fetchAllUsers();

    CompletableFuture<Collection<User>> fetchUsersBatch(int batchSize);
}
