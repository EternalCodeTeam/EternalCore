package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.database.UserRepository;
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
            this.updateNameIfChanged(cached, name);
            return CompletableFuture.completedFuture(cached);
        }

        return this.userRepository.getUser(uniqueId)
            .thenApply(optionalUser -> {
                User user = optionalUser.orElseGet(() -> this.createNewUser(uniqueId, name));
                this.add(user);
                return user;
            });
    }

    private User createNewUser(UUID uniqueId, String name) {
        User user = new User(uniqueId, name);
        this.userRepository.saveUser(user);
        return user;
    }

    private void updateNameIfChanged(User user, String name) {
        if (!user.getName().equals(name)) {
            User updated = new User(user.getUniqueId(), name);
            this.add(updated);
            this.userRepository.saveUser(updated);
        }
    }

    private void add(User user) {
        this.usersByUniqueId.put(user.getUniqueId(), user);
        this.usersByName.put(user.getName(), user);
    }
}
