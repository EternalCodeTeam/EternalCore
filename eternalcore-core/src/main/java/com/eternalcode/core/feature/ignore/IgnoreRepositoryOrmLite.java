package com.eternalcode.core.feature.ignore;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.ignore.event.IgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
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
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
class IgnoreRepositoryOrmLite extends AbstractRepositoryOrmLite implements IgnoreService {

    private static final UUID IGNORE_ALL = UUID.nameUUIDFromBytes("*".getBytes());

    private final Dao<IgnoreWrapper, Long> cachedDao;
    private final LoadingCache<UUID, Set<UUID>> ignores;
    private final EventCaller caller;
    private final Server server;

    @Inject
    private IgnoreRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler, EventCaller caller, Server server) throws SQLException {
        super(databaseManager, scheduler);
        this.cachedDao = databaseManager.getDao(IgnoreWrapper.class);
        this.caller = caller;
        this.server = server;

        this.ignores = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(15))
            .refreshAfterWrite(Duration.ofMinutes(3))
            .build(new IgnoreLoader());
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), IgnoreWrapper.class);
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
        Player senderPlayer = this.server.getPlayer(by);
        Player targetPlayer = null;

        if (!target.equals(IGNORE_ALL)) {
            targetPlayer = this.server.getPlayer(target);
        } else {
            IgnoreAllEvent event = this.caller.callEvent(new IgnoreAllEvent(senderPlayer));

            if (event.isCancelled()) {
                return CompletableFuture.completedFuture(null);
            }
        }

        IgnoreEvent event = this.caller.callEvent(new IgnoreEvent(senderPlayer, targetPlayer));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.runAsync(() -> {
            try {
                Set<UUID> uuids = this.ignores.get(by);

                if (!uuids.contains(target)) {
                    this.save(IgnoreWrapper.class, new IgnoreWrapper(by, target))
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
        Player senderPlayer = this.server.getPlayer(by);
        Player targetPlayer = this.server.getPlayer(target);

        UnIgnoreEvent event = this.caller.callEvent(new UnIgnoreEvent(senderPlayer, targetPlayer));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(null);
        }

        return this.action(IgnoreWrapper.class, dao -> {
                DeleteBuilder<IgnoreWrapper, Object> builder = dao.deleteBuilder();

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
        UnIgnoreAllEvent event = this.caller.callEvent(new UnIgnoreAllEvent(this.server.getPlayer(by)));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(null);
        }

        return this.action(IgnoreWrapper.class, dao -> {
                DeleteBuilder<IgnoreWrapper, Object> builder = dao.deleteBuilder();

                builder.where()
                    .eq("player_id", by);

                return builder.delete();
            })
            .thenRun(() -> this.ignores.refresh(by));
    }

    @Override
    public CompletableFuture<Void> purgeAll() {
        return this.deleteAll(IgnoreWrapper.class)
            .thenRun(this.ignores::invalidateAll);
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
