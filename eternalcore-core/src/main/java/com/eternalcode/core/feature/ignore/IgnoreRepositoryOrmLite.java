package com.eternalcode.core.feature.ignore;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
class IgnoreRepositoryOrmLite extends AbstractRepositoryOrmLite implements IgnoreRepository {

    private static final UUID IGNORE_ALL = UUID.nameUUIDFromBytes("*".getBytes());

    private final Dao<IgnoreTable, Long> cachedDao;
    private final LoadingCache<UUID, Set<UUID>> ignores;

    @Inject
    private IgnoreRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        this.cachedDao = databaseManager.getDao(IgnoreTable.class);

        this.ignores = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(15))
            .refreshAfterWrite(Duration.ofMinutes(3))
            .build(new IgnoreLoader());
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), IgnoreTable.class);
    }

    @Override
    public CompletableFuture<Boolean> isIgnored(UUID by, UUID target) {
        return CompletableFuture.supplyAsync(() -> {
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
    public CompletableFuture<Void> ignore(UUID by, UUID target) {
        return CompletableFuture.runAsync(() -> {
            try {
                Set<UUID> uuids = this.ignores.get(by);

                if (!uuids.contains(target)) {
                    this.save(IgnoreTable.class, new IgnoreTable(by, target))
                        .thenRun(() -> this.ignores.refresh(by));
                }
            }
            catch (ExecutionException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    @Override
    public CompletableFuture<Void> ignoreAll(UUID by) {
        return this.ignore(by, IGNORE_ALL);
    }

    @Override
    public CompletableFuture<Void> unIgnore(UUID by, UUID target) {
        return this.action(IgnoreTable.class, dao -> {
                DeleteBuilder<IgnoreTable, Object> builder = dao.deleteBuilder();

                builder.where()
                    .eq("player_id", by)
                    .and()
                    .eq("ignored_id", target);

                return builder.delete();
            })
            .thenRun(() -> this.ignores.refresh(by));
    }

    @Override
    public CompletableFuture<Void> unIgnoreAll(UUID by) {
        return this.action(IgnoreTable.class, dao -> {
                DeleteBuilder<IgnoreTable, Object> builder = dao.deleteBuilder();

                builder.where()
                    .eq("player_id", by);

                return builder.delete();
            })
            .thenRun(() -> this.ignores.refresh(by));
    }

    @ApiStatus.Internal
    @Override
    public CompletableFuture<Void> purgeAll() {
        return this.deleteAll(IgnoreTable.class)
            .thenRun(this.ignores::invalidateAll);
    }

    @DatabaseTable(tableName = "eternal_core_ignores")
    private static class IgnoreTable {

        @DatabaseField(id = true)
        Long id;

        @DatabaseField(columnName = "player_id", uniqueCombo = true)
        UUID playerUuid;

        @DatabaseField(columnName = "ignored_id", uniqueCombo = true)
        UUID ignoredUuid;

        IgnoreTable() {}

        IgnoreTable(UUID playerUuid, UUID ignoredUuid) {
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
                .map(ignoreTable -> ignoreTable.ignoredUuid)
                .collect(Collectors.toSet());
        }
    }
}
