package com.eternalcode.core.database.wrapper;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.ignore.IgnoreRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.scheduler.Scheduler;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.NotNull;
import panda.std.Blank;
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class IgnoreRepositoryOrmLite extends AbstractRepositoryOrmLite implements IgnoreRepository {

    private static final UUID IGNORE_ALL = UUID.nameUUIDFromBytes("*".getBytes());

    private final Dao<IgnoreWrapper, Long> cachedDao;
    private final LoadingCache<UUID, Set<UUID>> ignores;

    @Inject
    private IgnoreRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        this.cachedDao = databaseManager.getDao(IgnoreWrapper.class);
        this.ignores = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(15))
            .refreshAfterWrite(Duration.ofMinutes(3))
            .build(new IgnoreLoader());
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), IgnoreWrapper.class);
    }

    @Override
    public Completable<Boolean> isIgnored(UUID by, UUID target) {
        return this.scheduler.completeAsync(() -> {
            try {
                Set<UUID> uuids = this.ignores.get(by);

                return uuids.contains(target) || uuids.contains(IGNORE_ALL);
            }
            catch (ExecutionException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    @Override
    public Completable<Blank> ignore(UUID by, UUID target) {
        return this.scheduler.completeAsync(() -> {
            try {
                Set<UUID> uuids = this.ignores.get(by);

                if (uuids.contains(target)) {
                    return Blank.BLANK;
                }

                this.save(IgnoreWrapper.class, new IgnoreWrapper(by, target))
                    .then(integer -> this.ignores.refresh(by));
            }
            catch (ExecutionException exception) {
                throw new RuntimeException(exception);
            }

            return Blank.BLANK;
        });
    }

    @Override
    public Completable<Blank> ignoreAll(UUID by) {
        return this.ignore(by, IGNORE_ALL);
    }

    @Override
    public Completable<Blank> unIgnore(UUID by, UUID target) {
        return this.action(IgnoreWrapper.class, dao -> {
                DeleteBuilder<IgnoreWrapper, Object> builder = dao.deleteBuilder();

                builder.where()
                    .eq("player_id", by)
                    .and()
                    .eq("ignored_id", target);

                return builder.delete();
            })
            .then(integer -> this.ignores.refresh(by))
            .thenApply(integer -> Blank.BLANK);
    }

    @Override
    public Completable<Blank> unIgnoreAll(UUID by) {
        return this.action(IgnoreWrapper.class, dao -> {
                DeleteBuilder<IgnoreWrapper, Object> builder = dao.deleteBuilder();

                builder.where()
                    .eq("player_id", by);

                return builder.delete();
            })
            .then(integer -> this.ignores.refresh(by))
            .thenApply(integer -> Blank.BLANK);
    }

    @DatabaseTable(tableName = "eternal_core_ignores")
    private static class IgnoreWrapper {

        @DatabaseField(generatedId = true)
        Long id;

        @DatabaseField(columnName = "player_id", uniqueCombo = true)
        UUID playerUuid;

        @DatabaseField(columnName = "ignored_id", uniqueCombo = true)
        UUID ignoredUuid;

        IgnoreWrapper() {}

        IgnoreWrapper(UUID playerUuid, UUID ignoredUuid) {
            this.playerUuid = playerUuid;
            this.ignoredUuid = ignoredUuid;
        }

    }

    private class IgnoreLoader extends CacheLoader<UUID, Set<UUID>> {
        @Override
        public @NotNull Set<UUID> load(@NotNull UUID key) throws SQLException {
            return IgnoreRepositoryOrmLite.this.cachedDao.queryBuilder()
                .where().eq("player_id", key)
                .query()
                .stream()
                .map(ignoreWrapper -> ignoreWrapper.ignoredUuid)
                .collect(Collectors.toSet());
        }
    }

}
