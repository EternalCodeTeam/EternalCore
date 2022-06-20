package com.eternalcode.core.database.wrapper;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.home.Home;
import com.eternalcode.core.home.HomeRepository;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.user.User;
import com.j256.ormlite.table.TableUtils;
import panda.std.Blank;
import panda.std.Option;
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepositoryOrmLite extends AbstractRepositoryOrmLite implements HomeRepository {

    private RepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) {
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
        return this.action(HomeWrapper.class, dao -> Option.attempt(Throwable.class, () -> dao.queryBuilder()
                .where()
                .eq("owner", user.getUniqueId())
                .and()
                .eq("name", user.getName())
                .queryForFirst()
            ).map(HomeWrapper::toHome));
    }

    @Override
    public Completable<Blank> saveHome(Home home) {
        return this.save(HomeWrapper.class, HomeWrapper.from(home)).thenApply(ignore -> Blank.BLANK);
    }

    @Override
    public Completable<Blank> deleteHome(UUID uuid) {
        return this.deleteById(HomeWrapper.class, uuid).thenApply(ignore -> Blank.BLANK);
    }

    @Override
    public Completable<Blank> deleteHome(User user, String name) {
       return this.action(HomeWrapper.class, dao -> Option.attempt(Throwable.class, () -> dao.deleteBuilder()
            .where()
            .eq("owner", user.getUniqueId())
            .and()
            .eq("name", user.getName())
            .queryForFirst()
        )).thenApply(ignore -> Blank.BLANK);
    }

    @Override
    public Completable<Set<Home>> getHomes() {
        return this.selectAll(HomeWrapper.class)
            .thenApply(homeOrmLites -> homeOrmLites.stream().map(HomeWrapper::toHome).collect(Collectors.toSet()));
    }

    @Override
    public Completable<Set<Home>> getHomes(User user) {
        return this.action(HomeWrapper.class, dao -> Option.attempt(Throwable.class, () -> dao.queryBuilder()
            .where()
            .eq("owner", user.getUniqueId())
            .and()
            .eq("name", user.getName())
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

        return new RepositoryOrmLite(databaseManager, scheduler);
    }

}
