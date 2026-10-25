package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.database.UserRepository;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
class UserManagerImpl implements UserManager {

    private final Map<UUID, User> usersByUniqueId = new ConcurrentHashMap<>();
    private final Map<String, User> usersByName = new ConcurrentHashMap<>();
    private final UserRepository userRepository;

    @Inject
    UserManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(UUID uniqueId) {
        return Optional.ofNullable(this.usersByUniqueId.get(uniqueId));
    }

    @Override
    public Optional<User> getUser(String name) {
        return Optional.ofNullable(this.usersByName.get(name));
    }

    @Override
    public CompletableFuture<User> findOrCreate(UUID uniqueId, String name) {
        User cached = this.usersByUniqueId.get(uniqueId);

        if (cached != null) {
            return CompletableFuture.completedFuture(this.refresh(cached, name));
        }

        return this.userRepository.getUser(uniqueId)
            .thenApply(optionalUser -> optionalUser
                .map(existing -> this.refresh(existing, name))
                .orElseGet(() -> this.create(uniqueId, name)));
    }

    private User create(UUID uniqueId, String name) {
        User user = UserImpl.create(uniqueId, name, Instant.now(), Instant.now());
        this.cacheAndSave(user);
        return user;
    }

    private User refresh(User user, String name) {
        User refreshed = UserImpl.create(user.getUniqueId(), name, Instant.now(), user.getAccountCreated());

        this.usersByName.remove(user.getName(), user);
        this.cacheAndSave(refreshed);
        return refreshed;
    }

    private void cacheAndSave(User user) {
        this.usersByUniqueId.put(user.getUniqueId(), user);
        this.usersByName.put(user.getName(), user);
        this.userRepository.saveUser(user);
    }

    @Override
    public Collection<User> getUsers() {
        return List.copyOf(this.usersByUniqueId.values());
    }
}
