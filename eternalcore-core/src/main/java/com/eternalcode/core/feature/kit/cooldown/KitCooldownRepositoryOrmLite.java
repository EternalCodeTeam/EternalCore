package com.eternalcode.core.feature.kit.cooldown;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
class KitCooldownRepositoryOrmLite extends AbstractRepositoryOrmLite implements KitCooldownRepository {

    @Inject
    private KitCooldownRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), KitCooldownTable.class);
    }

    @Override
    public CompletableFuture<Optional<Instant>> findExpiration(UUID playerUniqueId, String kitName) {
        String id = KitCooldownTable.createId(playerUniqueId, kitName);

        return this.selectSafe(KitCooldownTable.class, id)
            .thenApply(table -> table.map(KitCooldownTable::getExpiresAt));
    }

    @Override
    public CompletableFuture<Map<String, Instant>> findExpirations(UUID playerUniqueId) {
        return this.action(KitCooldownTable.class, dao -> dao.queryBuilder()
                .where()
                .eq(KitCooldownTable.PLAYER_COLUMN, playerUniqueId)
                .query())
            .thenApply(tables -> tables.stream()
                .collect(Collectors.toUnmodifiableMap(KitCooldownTable::getKitName, KitCooldownTable::getExpiresAt)));
    }

    @Override
    public CompletableFuture<Void> save(UUID playerUniqueId, String kitName, Instant expiresAt) {
        return this.save(KitCooldownTable.class, new KitCooldownTable(playerUniqueId, kitName, expiresAt))
            .thenApply(status -> null);
    }

    @Override
    public CompletableFuture<Void> delete(UUID playerUniqueId, String kitName) {
        return this.deleteById(KitCooldownTable.class, KitCooldownTable.createId(playerUniqueId, kitName))
            .thenApply(deleted -> null);
    }

    @Override
    public CompletableFuture<Void> deleteByKit(String kitName) {
        return this.action(KitCooldownTable.class, dao -> {
            DeleteBuilder<KitCooldownTable, Object> builder = dao.deleteBuilder();
            builder.where().eq(KitCooldownTable.KIT_COLUMN, kitName);
            return builder.delete();
        }).thenApply(deleted -> null);
    }

    @Override
    public CompletableFuture<Void> deleteExpired(Instant now) {
        return this.action(KitCooldownTable.class, dao -> {
            DeleteBuilder<KitCooldownTable, Object> builder = dao.deleteBuilder();
            builder.where().le(KitCooldownTable.EXPIRES_AT_COLUMN, now.toEpochMilli());
            return builder.delete();
        }).thenApply(deleted -> null);
    }
}
