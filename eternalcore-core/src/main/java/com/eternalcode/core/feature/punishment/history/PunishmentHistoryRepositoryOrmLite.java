package com.eternalcode.core.feature.punishment.history;

import static com.eternalcode.core.feature.punishment.history.PunishmentHistoryTable.TARGET_UUID_COLUMN;
import static com.eternalcode.core.feature.punishment.history.PunishmentHistoryTable.TIMESTAMP_COLUMN;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
class PunishmentHistoryRepositoryOrmLite extends AbstractRepositoryOrmLite implements PunishmentHistoryRepository {

    private static final boolean DESCENDING = false;

    @Inject
    private PunishmentHistoryRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PunishmentHistoryTable.class);
    }

    @Override
    public CompletableFuture<Void> save(PunishmentHistoryEntry entry) {
        return this.action(PunishmentHistoryTable.class, dao -> dao.create(PunishmentHistoryTable.from(entry)))
            .thenApply(createdRows -> null);
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> findByTarget(UUID targetUuid, int page, int pageSize) {
        return this.action(PunishmentHistoryTable.class, dao -> {
            QueryBuilder<PunishmentHistoryTable, Object> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq(TARGET_UUID_COLUMN, targetUuid);
            return this.fetchPage(queryBuilder, page, pageSize);
        });
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> findRecent(int page, int pageSize) {
        return this.action(PunishmentHistoryTable.class, dao -> this.fetchPage(dao.queryBuilder(), page, pageSize));
    }

    private List<PunishmentHistoryEntry> fetchPage(
        QueryBuilder<PunishmentHistoryTable, Object> queryBuilder,
        int page,
        int pageSize
    ) throws SQLException {
        return queryBuilder
            .orderBy(TIMESTAMP_COLUMN, DESCENDING)
            .limit((long) pageSize)
            .offset((long) page * pageSize)
            .query()
            .stream()
            .map(PunishmentHistoryTable::toEntry)
            .toList();
    }
}
