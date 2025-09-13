package com.eternalcode.core.user.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.user.User;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public class UserRepositoryOrmLite extends AbstractRepositoryOrmLite implements UserRepository {

    @Inject
    public UserRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), UserTable.class);
    }

    @Override
    public CompletableFuture<Optional<User>> getUser(UUID uniqueId) {
        return this.selectSafe(UserTable.class, uniqueId)
            .thenApply(optional -> optional.map(UserTable::toUser));
    }

    @Override
    public CompletableFuture<Collection<User>> fetchAllUsers(Duration queryParameter) {
        return this.selectAll(UserTable.class).thenApply(userTable ->
            userTable.stream()
                .map(UserTable::toUser)
                .filter(user -> user.getLastLogin().isAfter(Instant.now().minus(queryParameter)))
                .toList()
        );
    }

    @Override
    public CompletableFuture<Void> saveUser(User user) {
        return this.save(UserTable.class, UserTable.from(user)).thenApply(v -> null);

        return this.upda
    }

    @Override
    public CompletableFuture<User> updateUser(User user) {
        return this.save(UserTable.class, UserTable.from(user)).thenApply(v -> user);
    }

    @Override
    public CompletableFuture<Void> deleteUser(UUID uniqueId) {
        return this.deleteById(UserTable.class, uniqueId).thenApply(v -> null);
    }

}
