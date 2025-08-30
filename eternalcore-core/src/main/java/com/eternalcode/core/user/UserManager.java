package com.eternalcode.core.user;

import com.eternalcode.commons.algorithm.BatchProcessor;
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
import java.util.function.Consumer;

@Service
public class UserManager {

    private final Cache<UUID, User> usersByUUID;
    private final Cache<String, User> usersByName;

    private final UserRepository userRepository;
    private final UserRepositorySettings userRepositorySettings;

    @Inject
    public UserManager(UserRepository userRepository, UserRepositorySettings userRepositorySettings) {
        this.userRepositorySettings = userRepositorySettings;
        this.usersByUUID = Caffeine.newBuilder().build();
        this.usersByName = Caffeine.newBuilder().build();

        this.userRepository = userRepository;

        fetchUsers();
    }

    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(this.usersByUUID.getIfPresent(uuid));
    }

    public Optional<User> getUser(String name) {
        return Optional.ofNullable(this.usersByName.getIfPresent(name));
    }

    public User getOrCreate(UUID uuid, String name) {
        User userByUUID = this.usersByUUID.getIfPresent(uuid);

        if (userByUUID != null) {
            return userByUUID;
        }

        User userByName = this.usersByName.getIfPresent(name);

        if (userByName != null) {
            return userByName;
        }

        this.userRepository.saveUser(new User(uuid, name));
        return this.create(uuid, name);
    }

    public User create(UUID uuid, String name) {
        if (this.usersByName.getIfPresent(name) != null || this.usersByUUID.getIfPresent(uuid) != null) {
            throw new IllegalStateException("User already exists");
        }

        User user = new User(uuid, name);
        this.usersByUUID.put(uuid, user);
        this.usersByName.put(name, user);

        this.userRepository.saveUser(user);
        return user;
    }

    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(this.usersByUUID.asMap().values());
    }

    private void fetchUsers() {
        if (this.userRepositorySettings.batchDatabaseFetchSize() <= 0) {
            throw new IllegalArgumentException("Value for batchDatabaseFetchSize must be greater than 0!");
        }

        Consumer<Collection<User>> batchSave = users ->
        {
            BatchProcessor<User> batchProcessor = new BatchProcessor<>(users, this.userRepositorySettings.batchDatabaseFetchSize());

            do {
                batchProcessor.processNext(user -> {
                    usersByName.put(user.getName(), user);
                    usersByUUID.put(user.getUniqueId(), user);
                });

            } while (!batchProcessor.isComplete());
        };

        if (this.userRepositorySettings.useBatchDatabaseFetching()) {
            this.userRepository.fetchUsersBatch(this.userRepositorySettings.batchDatabaseFetchSize())
                .thenAccept(batchSave);
        }
        else {

            this.userRepository.fetchAllUsers()
                .thenAccept(batchSave);

        }
    }
}
