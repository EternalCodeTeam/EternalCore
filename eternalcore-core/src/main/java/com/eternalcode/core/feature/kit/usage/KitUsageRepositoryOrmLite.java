package com.eternalcode.core.feature.kit.usage;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.kit.KitUsage;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
class KitUsageRepositoryOrmLite extends AbstractRepositoryOrmLite implements KitUsageRepository {

    @Inject
    private KitUsageRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), KitUsageTable.class);
    }

    @Override
    public CompletableFuture<Map<String, KitUsage>> findUsages(UUID playerUniqueId) {
        return this.action(KitUsageTable.class, dao -> dao.queryBuilder()
                .where()
                .eq(KitUsageTable.PLAYER_COLUMN, playerUniqueId)
                .query())
            .thenApply(tables -> tables.stream()
                .collect(Collectors.toUnmodifiableMap(KitUsageTable::getKitName, KitUsageTable::toUsage)));
    }

    @Override
    public CompletableFuture<Void> save(UUID playerUniqueId, String kitName, KitUsage usage) {
        return this.save(KitUsageTable.class, new KitUsageTable(playerUniqueId, kitName, usage))
            .thenApply(status -> null);
    }

    @Override
    public CompletableFuture<Void> resetClaim(UUID playerUniqueId, String kitName) {
        return this.action(KitUsageTable.class, dao -> {
            UpdateBuilder<KitUsageTable, Object> builder = dao.updateBuilder();
            builder.updateColumnValue(KitUsageTable.LAST_CLAIMED_AT_COLUMN, KitUsageTable.NEVER_CLAIMED);
            builder.where().idEq(KitUsageTable.createId(playerUniqueId, kitName));
            return builder.update();
        }).thenApply(updated -> null);
    }

    @Override
    public CompletableFuture<Void> deleteByKit(String kitName) {
        return this.action(KitUsageTable.class, dao -> {
            DeleteBuilder<KitUsageTable, Object> builder = dao.deleteBuilder();
            builder.where().eq(KitUsageTable.KIT_COLUMN, kitName);
            return builder.delete();
        }).thenApply(deleted -> null);
    }
}
