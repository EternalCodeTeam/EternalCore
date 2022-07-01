package com.eternalcode.core.database.wrapper;

import com.eternalcode.core.chat.feature.ingore.IgnoreRepository;
import com.eternalcode.core.database.DatabaseManager;
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
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class IgnoreRepositoryOrmLite extends AbstractRepositoryOrmLite implements IgnoreRepository {

    private final Dao<IgnoreWrapper, Long> dao;
    private final LoadingCache<UUID, Set<UUID>> ignores;

    private IgnoreRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) {
        super(databaseManager, scheduler);
        this.dao = databaseManager.getDao(IgnoreWrapper.class);
        this.ignores = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(30))
            .refreshAfterWrite(Duration.ofMinutes(3))
            .build(new IgnoreLoader());
    }

    @Override
    public Completable<Boolean> isIgnored(UUID by, UUID target) {
        return this.scheduler.completeAsync(() -> {
            try {
                return ignores.get(by).contains(target);
            } catch (ExecutionException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    @Override
    public void ignore(UUID by, UUID target) {
        this.save(IgnoreWrapper.class, new IgnoreWrapper(by, target))
            .then(integer -> this.ignores.refresh(by));
    }

    @Override
    public void unIgnore(UUID by, UUID target) {
        this.action(IgnoreWrapper.class, dao -> {
                DeleteBuilder<IgnoreWrapper, Object> builder = dao.deleteBuilder();

                builder.where()
                    .eq("player_id", by)
                    .and()
                    .eq("ignored_id", target);

                return builder.delete();
            }).then(integer -> this.ignores.refresh(by));
    }

    public static IgnoreRepositoryOrmLite create(DatabaseManager databaseManager, Scheduler scheduler) {
        try {
            TableUtils.createTableIfNotExists(databaseManager.connectionSource(), IgnoreWrapper.class);
        }
        catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return new IgnoreRepositoryOrmLite(databaseManager, scheduler);
    }

    private class IgnoreLoader extends CacheLoader<UUID, Set<UUID>> {
        @Override
        public @NotNull Set<UUID> load(@NotNull UUID key) throws SQLException {
            return dao.queryBuilder()
                .where().eq("player_id", key)
                .query()
                .stream()
                .map(ignoreWrapper -> ignoreWrapper.ignoredUuid)
                .collect(Collectors.toSet());
        }
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

}
