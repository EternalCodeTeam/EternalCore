package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.database.UserRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Service
public class UserManager {

    private final Cache<UUID, User> usersByUUID;
    private final Cache<String, User> usersByName;

    private final UserRepository userRepository;

    @Inject
    public UserManager(UserRepository userRepository) {
        this.usersByUUID = Caffeine.newBuilder().build();
        this.usersByName = Caffeine.newBuilder().build();

        this.userRepository = userRepository;
        this.fetchActiveUsers();
    }

    public Optional<User> getUser(UUID uniqueId) {
        return Optional.ofNullable(this.usersByUUID.getIfPresent(uniqueId));
    }

    public Optional<User> getUser(String name) {
        return Optional.ofNullable(this.usersByName.getIfPresent(name));
    }

    public CompletableFuture<Optional<User>> getUserFromRepository(UUID uniqueId) {
        return this.userRepository.getUser(uniqueId);
    }

    public CompletableFuture<Optional<User>> getUserFromRepository(String name) {
        return this.userRepository.getUser(name);
    }

    public void saveUser(User user) {
        this.saveInCache(user);
        this.userRepository.saveUser(user);
    }

    public void updateLastSeen(UUID uniqueId, String name) {
        this.userRepository.updateUser(uniqueId, name).thenAccept(this::saveInCache);
    }

    private void fetchActiveUsers() {
        this.userRepository.getActiveUsers().thenAccept(list -> list.forEach(this::saveInCache));
    }

    private void saveInCache(User user) {
        this.usersByUUID.put(user.getUniqueId(), user);
        this.usersByName.put(user.getName(), user);
    }
}
