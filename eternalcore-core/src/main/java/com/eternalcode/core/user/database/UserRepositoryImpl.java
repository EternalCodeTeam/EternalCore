package com.eternalcode.core.user.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.user.User;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public class UserRepositoryImpl extends AbstractRepositoryOrmLite implements UserRepository {

    private static final String NAME_COLUMN = "name";

    @Inject
    public UserRepositoryImpl(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), UserTable.class);
    }

    @Override
    public CompletableFuture<Optional<User>> getUser(UUID uniqueId) {
        return this.selectSafe(UserTable.class, uniqueId)
            .thenApply(optional -> optional.map(UserTable::toUser));
    }

    @Override
    public CompletableFuture<Optional<User>> getUser(String name) {
        return this.action(
            UserTable.class,
            dao -> Optional.ofNullable(
                dao.queryBuilder()
                    .where()
                    .eq(NAME_COLUMN, name)
                    .queryForFirst()
            ).map(UserTable::toUser)
        );
    }

    @Override
    public CompletableFuture<Void> saveUser(User user) {
        return this.save(UserTable.class, UserTable.from(user))
            .thenApply(status -> null);
    }

}
