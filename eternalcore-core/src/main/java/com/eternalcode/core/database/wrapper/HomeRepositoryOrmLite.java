package com.eternalcode.core.database.wrapper;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.persister.LocationPersister;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.user.User;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import org.bukkit.Location;
import panda.std.Blank;
import panda.std.Option;
import panda.std.Result;
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HomeRepositoryOrmLite extends AbstractRepositoryOrmLite implements HomeRepository {

    @Inject
    private HomeRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) {
        super(databaseManager, scheduler);
    }

    @Override
    public Completable<Option<Home>> getHome(UUID uuid) {
        return this.select(HomeWrapper.class, uuid)
            .thenApply(Option::of)
            .thenApply(home -> home.map(HomeWrapper::toHome));
    }

    @Override
    public Completable<Option<Home>> getHome(User user, String name) {
        return this.action(HomeWrapper.class, dao -> Option.supplyThrowing(Throwable.class, () -> dao.queryBuilder()
            .where()
            .eq("owner", user.getUniqueId())
            .and()
            .eq("name", name)
            .queryForFirst()
        ).map(HomeWrapper::toHome));
    }

    @Override
    public Completable<Blank> saveHome(Home home) {
        return this.save(HomeWrapper.class, HomeWrapper.from(home)).thenApply(ignore -> Blank.BLANK);
    }

    @Override
    public Completable<Integer> deleteHome(UUID uuid) {
        return this.deleteById(HomeWrapper.class, uuid);
    }

    @Override
    public Completable<Integer> deleteHome(User user, String name) {
        return this.action(HomeWrapper.class, dao -> Result.supplyThrowing(Throwable.class, () -> {
            DeleteBuilder<HomeWrapper, Object> builder = dao.deleteBuilder();
            builder.where()
                .eq("owner", user.getUniqueId())
                .and()
                .eq("name", name);

            return builder.delete();
        }).onError(Throwable::printStackTrace).orElseGet(throwable -> 0));
    }

    @Override
    public Completable<Set<Home>> getHomes() {
        return this.selectAll(HomeWrapper.class)
            .thenApply(homeOrmLites -> homeOrmLites.stream().map(HomeWrapper::toHome).collect(Collectors.toSet()));
    }

    @Override
    public Completable<Set<Home>> getHomes(User user) {
        return this.action(HomeWrapper.class, dao -> Option.supplyThrowing(Throwable.class, () -> dao.queryBuilder()
            .where()
            .eq("owner", user.getUniqueId())
            .query()
        )).thenApply(option -> option.map(homes -> homes.stream()
            .map(HomeWrapper::toHome)
            .collect(Collectors.toSet())
        ).orElseGet(new HashSet<>()));
    }

    public static HomeRepository create(DatabaseManager databaseManager, Scheduler scheduler) {
        try {
            TableUtils.createTableIfNotExists(databaseManager.connectionSource(), HomeWrapper.class);
        }
        catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return new HomeRepositoryOrmLite(databaseManager, scheduler);
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
