package com.eternalcode.core.test;

import com.eternalcode.core.user.User;
import com.eternalcode.core.user.database.UserRepository;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MockUserRepository implements UserRepository {

    @Override
    public CompletableFuture<List<User>> getActiveUsers() {
        return null;
    }

    @Override
    public CompletableFuture<Optional<User>> getUser(UUID uniqueId) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<User> saveUser(User player) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<User> updateUser(UUID uniqueId, String name) {
        return CompletableFuture.completedFuture(null);
    }

}
