package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.database.UserRepository;
import com.eternalcode.core.user.database.UserRepositorySettings;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
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
    private boolean fetched = false;

    private final UserRepository userRepository;
    private final UserRepositorySettings userRepositorySettings;

    @Inject
    public UserManager(UserRepository userRepository, UserRepositorySettings userRepositorySettings) {
        this.userRepositorySettings = userRepositorySettings;
        this.usersByUUID = Caffeine.newBuilder().build();
        this.usersByName = Caffeine.newBuilder().build();

        this.userRepository = userRepository;

        fetchUsers().thenRun(() -> this.fetched = true);
    }

    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(this.usersByUUID.getIfPresent(uuid));
    }

    public Optional<User> getUser(String name) {
        return Optional.ofNullable(this.usersByName.getIfPresent(name.toLowerCase()));
    }

    public User getOrCreate(UUID uuid, String name) {
        if (!this.fetched) {
            throw new IllegalStateException("Users have not been fetched from the database yet!");
        }

        User userByUUID = this.usersByUUID.getIfPresent(uuid);

        if (userByUUID != null) {
            return userByUUID;
        }

        User userByName = this.usersByName.getIfPresent(name.toLowerCase());

        if (userByName != null) {
            return userByName;
        }

        return this.create(uuid, name);
    }

    public User create(UUID uuid, String name) {
        if (this.usersByName.getIfPresent(name.toLowerCase()) != null || this.usersByUUID.getIfPresent(uuid) != null) {
            throw new IllegalStateException("User already exists");
        }

        User user = new User(uuid, name);
        this.usersByUUID.put(uuid, user);
        this.usersByName.put(name.toLowerCase(), user);

        this.userRepository.saveUser(user);
        return user;
    }

    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(this.usersByUUID.asMap().values());
    }

    private CompletableFuture<Void> fetchUsers() {
        if (this.userRepositorySettings.batchDatabaseFetchSize() <= 0) {
            throw new IllegalArgumentException("Value for batchDatabaseFetchSize must be greater than 0!");
        }

        Consumer<Collection<User>> batchSave = users -> users.forEach(user -> {
            this.usersByName.put(user.getName(), user);
            this.usersByUUID.put(user.getUniqueId(), user);
        });

        if (this.userRepositorySettings.useBatchDatabaseFetching()) {
            this.userRepository.fetchUsersBatch(this.userRepositorySettings.batchDatabaseFetchSize()).thenAccept(batchSave);
        }
        else {
            this.userRepository.fetchAllUsers().thenAccept(batchSave);
        }

        return CompletableFuture.completedFuture(null);
    }
}
