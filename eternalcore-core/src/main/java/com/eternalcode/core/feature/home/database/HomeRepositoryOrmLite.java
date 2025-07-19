package com.eternalcode.core.feature.home.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.user.User;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
class HomeRepositoryOrmLite extends AbstractRepositoryOrmLite implements HomeRepository {

    private static final String OWNER_COLUMN = "owner";
    private static final String NAME_COLUMN = "name";

    @Inject
    private HomeRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), HomeTable.class);
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId) {
        return this.select(HomeTable.class, playerUniqueId)
            .thenApply(Optional::of)
            .thenApply(home -> home.map(HomeTable::toHome));
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(User user, String homeName) {
        return this.action(HomeTable.class, dao -> Optional.ofNullable(dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, user.getUniqueId())
            .and()
            .eq(NAME_COLUMN, homeName)
            .queryForFirst()).map(HomeTable::toHome));
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId, String homeName) {
        return this.action(HomeTable.class, dao -> Optional.ofNullable(dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, playerUniqueId)
            .and()
            .eq(NAME_COLUMN, homeName)
            .queryForFirst()).map(HomeTable::toHome));
    }

    @Override
    public CompletableFuture<Void> saveHome(Home home) {
        return this.save(HomeTable.class, HomeTable.from(home)).thenApply(result -> null);
    }

    @Override
    public CompletableFuture<Integer> deleteHome(UUID playerUniqueId) {
        return this.deleteById(HomeTable.class, playerUniqueId);
    }

    @Override
    public CompletableFuture<Integer> deleteHome(User user, String homeName) {
        return this.action(HomeTable.class, dao -> {
            DeleteBuilder<HomeTable, Object> builder = dao.deleteBuilder();
            builder.where()
                .eq(OWNER_COLUMN, user.getUniqueId())
                .and()
                .eq(NAME_COLUMN, homeName);
            return builder.delete();
        });
    }

    @Override
    public CompletableFuture<Integer> deleteHome(UUID playerUniqueId, String homeName) {
        return this.action(HomeTable.class, dao -> {
            DeleteBuilder<HomeTable, Object> builder = dao.deleteBuilder();
            builder.where()
                .eq(OWNER_COLUMN, playerUniqueId)
                .and()
                .eq(NAME_COLUMN, homeName);
            return builder.delete();
        });
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes() {
        return this.selectAll(HomeTable.class)
            .thenApply(homeOrmLites -> homeOrmLites.stream().map(HomeTable::toHome).collect(Collectors.toSet()));
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes(User user) {
        return this.action(HomeTable.class, dao -> dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, user.getUniqueId())
            .query()).thenApply(homes -> homes.stream()
            .map(HomeTable::toHome)
            .collect(Collectors.toSet()));
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes(UUID playerUniqueId) {
        return this.action(HomeTable.class, dao -> dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, playerUniqueId)
            .query()).thenApply(homes -> homes.stream()
            .map(HomeTable::toHome)
            .collect(Collectors.toSet()));
    }
}
