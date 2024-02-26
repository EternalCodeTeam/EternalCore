package com.eternalcode.core.feature.home.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
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
class HomeServiceOrmLite extends AbstractRepositoryOrmLite implements HomeRepository {

    private static final String OWNER_COLUMN = "owner";
    private static final String NAME_COLUMN = "name";

    @Inject
    private HomeServiceOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), HomeWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId) {
        return this.select(HomeWrapper.class, playerUniqueId)
            .thenApply(Optional::of)
            .thenApply(home -> home.map(HomeWrapper::toHome));
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(User user, String homeName) {
        return this.action(HomeWrapper.class, dao -> Optional.ofNullable(dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, user.getUniqueId())
            .and()
            .eq(NAME_COLUMN, homeName)
            .queryForFirst()).map(HomeWrapper::toHome));
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId, String homeName) {
        return this.action(HomeWrapper.class, dao -> Optional.ofNullable(dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, playerUniqueId)
            .and()
            .eq(NAME_COLUMN, homeName)
            .queryForFirst()).map(HomeWrapper::toHome));
    }

    @Override
    public CompletableFuture<Void> saveHome(Home home) {
        return this.save(HomeWrapper.class, HomeWrapper.from(home)).thenApply(result -> null);
    }

    @Override
    public CompletableFuture<Integer> deleteHome(UUID playerUniqueId) {
        return this.deleteById(HomeWrapper.class, playerUniqueId);
    }

    @Override
    public CompletableFuture<Integer> deleteHome(User user, String homeName) {
        return this.action(HomeWrapper.class, dao -> {
            DeleteBuilder<HomeWrapper, Object> builder = dao.deleteBuilder();
            builder.where()
                .eq(OWNER_COLUMN, user.getUniqueId())
                .and()
                .eq(NAME_COLUMN, homeName);
            return builder.delete();
        });
    }

    @Override
    public CompletableFuture<Integer> deleteHome(UUID playerUniqueId, String homeName) {
        return null;
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes() {
        return this.selectAll(HomeWrapper.class)
            .thenApply(homeOrmLites -> homeOrmLites.stream().map(HomeWrapper::toHome).collect(Collectors.toSet()));
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes(User user) {
        return this.action(HomeWrapper.class, dao -> dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, user.getUniqueId())
            .query()).thenApply(homes -> homes.stream()
            .map(HomeWrapper::toHome)
            .collect(Collectors.toSet()));
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes(UUID playerUniqueId) {
        return this.action(HomeWrapper.class, dao -> dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, playerUniqueId)
            .query()).thenApply(homes -> homes.stream()
            .map(HomeWrapper::toHome)
            .collect(Collectors.toSet()));
    }
}
