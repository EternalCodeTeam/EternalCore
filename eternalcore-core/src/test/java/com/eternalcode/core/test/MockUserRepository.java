package com.eternalcode.core.test;

import com.eternalcode.core.user.User;
import com.eternalcode.core.user.database.UserRepository;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MockUserRepository implements UserRepository {
    @Override
    public CompletableFuture<User> getUser(UUID uniqueId) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> saveUser(User player) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<User> updateUser(UUID uniqueId, User player) {
        return CompletableFuture.completedFuture(player);
    }

    @Override
    public CompletableFuture<Void> deleteUser(UUID uniqueId) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Collection<User>> fetchAllUsers() {
        return CompletableFuture.completedFuture(java.util.List.of());
    }

    @Override
    public CompletableFuture<Collection<User>> fetchUsersBatch(int batchSize) {
        return CompletableFuture.completedFuture(java.util.List.of());
    }
}
