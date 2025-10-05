package com.eternalcode.core.user.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.user.User;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.Blocking;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public class UserRepositoryOrmLite extends AbstractRepositoryOrmLite implements UserRepository {

    private static final Duration WEEK = Duration.ofDays(7);
    private static final String NAME_COLUMN = "name";

    @Inject
    public UserRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), UserTable.class);
    }

    @Blocking
    public CompletableFuture<List<User>> getActiveUsers() {
        return this.selectAll(UserTable.class)
            .thenApply(userTables -> userTables.stream().map(UserTable::toUser).toList())
            .thenApply(users -> users.stream().filter(user -> user.getLastLogin().isAfter(Instant.now().minus(WEEK))).toList());
    }

    @Override
    @Blocking
    public CompletableFuture<Optional<User>> getUser(UUID uniqueId) {
        return this.selectSafe(UserTable.class, uniqueId)
            .thenApply(optional -> optional.map(UserTable::toUser));
    }

    @Override
    @Blocking
    public CompletableFuture<Optional<User>> getUser(String name) {
        return this.action(UserTable.class, dao -> Optional.ofNullable(dao.queryBuilder()
            .where()
            .eq(NAME_COLUMN, name)
            .queryForFirst().toUser())
        );
    }

    @Override
    @Blocking
    public CompletableFuture<User> saveUser(User user) {
        return this.saveIfNotExist(UserTable.class, UserTable.from(user)).thenApply(UserTable::toUser);
    }

    @Override
    @Blocking
    public CompletableFuture<User> updateUser(UUID uniqueId, String name) {
        Instant now = Instant.now();
        return this.selectSafe(UserTable.class, uniqueId)
            .thenApply(optional -> optional.map(UserTable::toUser))
            .thenApply(optionalUser -> optionalUser.orElse(new User(uniqueId, name, now, now)))
            .thenApply(user -> new User(user.getUniqueId(), user.getName(), user.getCreated(), now))
            .thenApply(user -> {
                this.save(UserTable.class, UserTable.from(user));
                return user;
            });
    }
}
