package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.database.UserRepository;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserManager {

    private final Map<UUID, User> usersByUniqueId = new ConcurrentHashMap<>();
    private final Map<String, User> usersByName = new ConcurrentHashMap<>();
    private final UserRepository userRepository;

    @Inject
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(UUID uniqueId) {
        return Optional.ofNullable(this.usersByUniqueId.get(uniqueId));
    }

    public Optional<User> getUser(String name) {
        return Optional.ofNullable(this.usersByName.get(name));
    }

    public CompletableFuture<User> findOrCreate(UUID uniqueId, String name) {
        User cached = this.usersByUniqueId.get(uniqueId);

        if (cached != null) {
            User updated = this.updateLastLogin(cached, name);
            this.add(updated);
            this.userRepository.saveUser(updated);
            return CompletableFuture.completedFuture(updated);
        }

        return this.userRepository.getUser(uniqueId)
            .thenApply(optionalUser -> {
                User user = optionalUser
                    .map(existing -> this.updateLastLogin(existing, name))
                    .orElseGet(() -> this.createNewUser(uniqueId, name));

                this.add(user);
                this.userRepository.saveUser(user);
                return user;
            });
    }

    private User createNewUser(UUID uniqueId, String name) {
        Instant now = Instant.now();
        return new User(uniqueId, name, now, now);
    }

    public CompletableFuture<Void> updateLastSeen(UUID uniqueId, String name) {
        User cached = this.usersByUniqueId.get(uniqueId);

        if (cached == null) {
            return CompletableFuture.completedFuture(null);
        }

        User updated = this.updateLastLogin(cached, name);
        this.add(updated);
        return this.userRepository.saveUser(updated);
    }

    private User updateLastLogin(User user, String name) {
        Instant now = Instant.now();
        return new User(user.getUniqueId(), name, user.getCreated(), now);
    }

    private void add(User user) {
        this.usersByUniqueId.put(user.getUniqueId(), user);
        this.usersByName.put(user.getName(), user);
    }
}
