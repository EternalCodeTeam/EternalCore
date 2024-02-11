package com.eternalcode.core.feature.home;

import com.eternalcode.commons.shared.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.persister.LocationPersister;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.user.User;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
class HomeRepositoryOrmLite extends AbstractRepositoryOrmLite implements HomeRepository {

    @Inject
    private HomeRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), HomeWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(UUID uuid) {
        return this.select(HomeWrapper.class, uuid)
            .thenApply(Optional::of)
            .thenApply(home -> home.map(HomeWrapper::toHome));
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(User user, String name) {
        return this.action(HomeWrapper.class, dao -> Optional.ofNullable(dao.queryBuilder()
            .where()
            .eq("owner", user.getUniqueId())
            .and()
            .eq("name", name)
            .queryForFirst()).map(HomeWrapper::toHome));
    }

    @Override
    public CompletableFuture<Void> saveHome(Home home) {
        return this.save(HomeWrapper.class, HomeWrapper.from(home)).thenApply(result -> null);
    }

    @Override
    public CompletableFuture<Integer> deleteHome(UUID uuid) {
        return this.deleteById(HomeWrapper.class, uuid);
    }

    @Override
    public CompletableFuture<Integer> deleteHome(User user, String name) {
        return this.action(HomeWrapper.class, dao -> {
            DeleteBuilder<HomeWrapper, Object> builder = dao.deleteBuilder();
            builder.where()
                .eq("owner", user.getUniqueId())
                .and()
                .eq("name", name);
            return builder.delete();
        });
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
            .eq("owner", user.getUniqueId())
            .query()).thenApply(homes -> homes.stream()
            .map(HomeWrapper::toHome)
            .collect(Collectors.toSet()));
    }

    @DatabaseTable(tableName = "eternal_core_homes")
    static class HomeWrapper {

        @DatabaseField(columnName = "id", id = true)
        private UUID uuid;

        @DatabaseField(columnName = "owner")
        private UUID owner;

        @DatabaseField(columnName = "name")
        private String name;

        @DatabaseField(columnName = "location", persisterClass = LocationPersister.class)
        private Location location;

        HomeWrapper() {
        }

        HomeWrapper(UUID uuid, UUID owner, String name, Location location) {
            this.uuid = uuid;
            this.owner = owner;
            this.name = name;
            this.location = location;
        }

        Home toHome() {
            return new Home(this.uuid, this.owner, this.name, this.location);
        }

        static HomeWrapper from(Home home) {
            return new HomeWrapper(home.getUuid(), home.getOwner(), home.getName(), home.getLocation());
        }
    }
}
