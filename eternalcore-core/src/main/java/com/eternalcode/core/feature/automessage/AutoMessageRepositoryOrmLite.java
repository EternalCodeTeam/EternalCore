package com.eternalcode.core.feature.automessage;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
class AutoMessageRepositoryOrmLite extends AbstractRepositoryOrmLite implements AutoMessageRepository {

    @Inject
    private AutoMessageRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), AutoMessageTable.class);
    }

    @Override
    public CompletableFuture<Set<UUID>> findReceivers(Set<UUID> onlineUniqueIds) {
        if (onlineUniqueIds.isEmpty()) {
            return CompletableFuture.completedFuture(onlineUniqueIds);
        }

        CompletableFuture<List<AutoMessageTable>> wrapperList =
            this.action(
                AutoMessageTable.class, dao -> {
                Where<AutoMessageTable, Object> where = dao.queryBuilder().where();
                where.in("unique_id", onlineUniqueIds);
                return where.query();
            });

        return wrapperList.thenApply(ignores -> {
            Set<UUID> ignoredIds = ignores.stream()
                .map(wrapper -> wrapper.uniqueId)
                .collect(Collectors.toSet());

            return onlineUniqueIds.stream()
                .filter(onlineUniqueId -> !ignoredIds.contains(onlineUniqueId))
                .collect(Collectors.toSet());
        });
    }

    @Override
    public CompletableFuture<Boolean> isReceiving(UUID uniqueId) {
        return this.selectSafe(AutoMessageTable.class, uniqueId).thenApply(Optional::isEmpty);
    }

    @Override
    public CompletableFuture<Boolean> switchReceiving(UUID uniqueId) {
        return this.selectSafe(AutoMessageTable.class, uniqueId).thenCompose(optional -> {
            if (optional.isEmpty()) {
                return this.save(AutoMessageTable.class, new AutoMessageTable(uniqueId))
                    .thenApply(result -> false);
            }

            AutoMessageTable wrapper = optional.get();
            return this.delete(AutoMessageTable.class, wrapper).thenApply(state -> true);
        });
    }
}
