package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.database.UserRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
class UserManagerImpl implements UserManager {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();
    private final Map<String, UUID> names = new ConcurrentHashMap<>();

    private final UserRepository userRepository;

    @Inject
    UserManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(UUID uniqueId) {
        return Optional.ofNullable(this.users.get(uniqueId));
    }

    @Override
    public Optional<User> getUser(String name) {
        UUID uniqueId = this.names.get(normalize(name));

        return uniqueId == null
            ? Optional.empty()
            : this.getUser(uniqueId);
    }

    @Override
    public Collection<User> getUsers() {
        return List.copyOf(this.users.values());
    }

    @Override
    public CompletableFuture<User> findOrCreate(UUID uniqueId, String name) {
        User cached = this.users.get(uniqueId);

        if (cached != null) {
            return this.refreshName(cached, name);
        }

        return this.userRepository.getUser(uniqueId)
            .thenCompose(optional -> optional
                .map(user -> this.refreshName(user, name))
                .orElseGet(() -> this.create(uniqueId, name)));
    }

    private CompletableFuture<User> create(UUID uniqueId, String name) {
        Instant now = Instant.now();

        User user = UserImpl.create(
            uniqueId,
            name,
            now,
            now
        );

        return this.saveAndCache(user);
    }

    private CompletableFuture<User> refreshName(User user, String name) {
        if (user.getName().equals(name)) {
            this.cache(user);
            return CompletableFuture.completedFuture(user);
        }

        User updated = UserImpl.create(
            user.getUniqueId(),
            name,
            user.getLastSeen(),
            user.getAccountCreated()
        );

        return this.saveAndCache(updated);
    }

    private CompletableFuture<User> saveAndCache(User user) {
        return this.userRepository.saveUser(user)
            .thenApply(ignored -> {
                this.cache(user);
                return user;
            });
    }

    private synchronized void cache(User user) {
        User previous = this.users.put(user.getUniqueId(), user);

        if (previous != null) {
            this.names.remove(
                normalize(previous.getName()),
                previous.getUniqueId()
            );
        }

        this.names.put(normalize(user.getName()), user.getUniqueId());
    }

    private static String normalize(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}
