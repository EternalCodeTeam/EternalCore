package com.eternalcode.core.feature.enderchest.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseException;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.enderchest.EnderchestWrite;
import com.eternalcode.core.feature.enderchest.PageContents;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.db.MysqlDatabaseType;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Repository
class EnderchestRepositoryOrmLite extends AbstractRepositoryOrmLite implements EnderchestRepository {

    private static final String WIDEN_CONTENTS_COLUMN = "ALTER TABLE " + EnderchestPageTable.TABLE_NAME
        + " MODIFY " + EnderchestPageTable.CONTENTS_COLUMN + " MEDIUMBLOB";

    private static final String CONTENTS_COLUMN_TYPE_QUERY = "SELECT DATA_TYPE FROM information_schema.COLUMNS"
        + " WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? AND COLUMN_NAME = ?";

    private static final Set<String> WIDE_BLOB_TYPES = Set.of("mediumblob", "longblob");

    private final ExecutorService writer = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "EternalCore Enderchest Writer");
        thread.setDaemon(true);
        return thread;
    });

    private final Logger logger;

    @Inject
    private EnderchestRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler, Logger logger) throws SQLException {
        super(databaseManager, scheduler);
        this.logger = logger;

        ConnectionSource connectionSource = databaseManager.connectionSource();
        TableUtils.createTableIfNotExists(connectionSource, EnderchestPageTable.class);

        if (connectionSource.getDatabaseType() instanceof MysqlDatabaseType) {
            this.widenContentsColumn(databaseManager.getDao(EnderchestPageTable.class));
        }
    }

    @Override
    public CompletableFuture<List<PageContents>> findPages(UUID ownerUniqueId) {
        return this.action(EnderchestPageTable.class, dao -> dao.queryBuilder()
            .orderBy(EnderchestPageTable.PAGE_COLUMN, true)
            .where()
            .eq(EnderchestPageTable.OWNER_COLUMN, ownerUniqueId)
            .query()
            .stream()
            .map(EnderchestPageTable::toContents)
            .toList());
    }

    @Override
    public CompletableFuture<Void> savePages(UUID ownerUniqueId, EnderchestWrite write) {
        try {
            return CompletableFuture.runAsync(() -> this.writePages(ownerUniqueId, write), this.writer);
        }
        catch (RejectedExecutionException exception) {
            return CompletableFuture.failedFuture(
                new DatabaseException("Ender chest writes are already closed, cannot save pages of " + ownerUniqueId, exception));
        }
    }

    private void writePages(UUID ownerUniqueId, EnderchestWrite write) {
        try {
            this.writeInTransaction(this.databaseManager.getDao(EnderchestPageTable.class), ownerUniqueId, write);
        }
        catch (Exception exception) {
            throw new DatabaseException("Failed to save ender chest pages of " + ownerUniqueId, exception);
        }
    }

    @Override
    public void shutdownWrites(Duration timeout) {
        this.writer.shutdown();

        try {
            if (!this.writer.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS)) {
                this.logger.severe("Ender chest writes did not reach the database within " + timeout.toSeconds() + "s, the remaining ones are lost");
            }
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private void writeInTransaction(Dao<EnderchestPageTable, String> dao, UUID ownerUniqueId, EnderchestWrite write) throws SQLException {
        boolean replaceAll = write.replaceAll();

        TransactionManager.callInTransaction(dao.getConnectionSource(), () -> {
            if (replaceAll) {
                DeleteBuilder<EnderchestPageTable, String> deleteBuilder = dao.deleteBuilder();
                deleteBuilder.where().eq(EnderchestPageTable.OWNER_COLUMN, ownerUniqueId);
                deleteBuilder.delete();
            }

            for (PageContents page : write.pages()) {
                if (!page.isEmpty()) {
                    dao.createOrUpdate(EnderchestPageTable.from(ownerUniqueId, page));
                    continue;
                }

                if (!replaceAll) {
                    dao.deleteById(EnderchestPageTable.idOf(ownerUniqueId, page.page()));
                }
            }

            return null;
        });
    }

    private void widenContentsColumn(Dao<EnderchestPageTable, String> dao) throws SQLException {
        if (this.hasWideContentsColumn(dao)) {
            return;
        }

        dao.executeRawNoArgs(WIDEN_CONTENTS_COLUMN);
    }

    private boolean hasWideContentsColumn(Dao<EnderchestPageTable, String> dao) throws SQLException {
        try (GenericRawResults<String[]> results = dao.queryRaw(
            CONTENTS_COLUMN_TYPE_QUERY,
            EnderchestPageTable.TABLE_NAME,
            EnderchestPageTable.CONTENTS_COLUMN
        )) {
            String[] row = results.getFirstResult();

            return row != null && row.length > 0 && WIDE_BLOB_TYPES.contains(row[0].toLowerCase(Locale.ROOT));
        }
        catch (Exception exception) {
            throw new SQLException("Failed to read the type of the ender chest contents column", exception);
        }
    }
}
