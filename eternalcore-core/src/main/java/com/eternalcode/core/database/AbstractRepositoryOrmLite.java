package com.eternalcode.core.database;

import com.eternalcode.commons.ThrowingFunction;
import com.eternalcode.commons.scheduler.Scheduler;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractRepositoryOrmLite {

    protected final DatabaseManager databaseManager;
    protected final Scheduler scheduler;

    protected AbstractRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) {
        this.databaseManager = databaseManager;
        this.scheduler = scheduler;
    }

    protected <T> CompletableFuture<Dao.CreateOrUpdateStatus> save(Class<T> type, T warp) {
        return this.action(type, dao -> dao.createOrUpdate(warp));
    }

    protected <T> CompletableFuture<T> saveIfNotExist(Class<T> type, T warp) {
        return this.action(type, dao -> dao.createIfNotExists(warp));
    }

    protected <T, ID> CompletableFuture<T> select(Class<T> type, ID id) {
        return this.action(type, dao -> dao.queryForId(id));
    }

    protected <T, ID> CompletableFuture<Optional<T>> selectSafe(Class<T> type, ID id) {
        return this.action(type, dao -> Optional.ofNullable(dao.queryForId(id)));
    }

    protected <T> CompletableFuture<Integer> delete(Class<T> type, T warp) {
        return this.action(type, dao -> dao.delete(warp));
    }

    protected <T> CompletableFuture<Integer> deleteAll(Class<T> type) {
        return this.action(type, dao -> dao.deleteBuilder().delete());
    }

    protected <T, ID> CompletableFuture<Integer> deleteById(Class<T> type, ID id) {
        return this.action(type, dao -> dao.deleteById(id));
    }

    protected <T> CompletableFuture<List<T>> selectAll(Class<T> type) {
        return this.action(type, Dao::queryForAll);
    }

    protected <T, ID, R> CompletableFuture<R> action(
        Class<T> type,
        ThrowingFunction<Dao<T, ID>, R, SQLException> action
    ) {
        CompletableFuture<R> future = new CompletableFuture<>();

        this.scheduler.runAsync(() -> {
            Dao<T, ID> dao;
            try {
                dao = this.databaseManager.getDao(type);
            }
            catch (Exception exception) {
                future.completeExceptionally(new DatabaseException(
                    "Failed to get DAO for class: " + type.getName(), exception));
                return;
            }

            try {
                future.complete(action.apply(dao));
            }
            catch (Throwable throwable) {
                future.completeExceptionally(new DatabaseException(
                    "Database action failed for: " + type.getSimpleName(), throwable));
            }
        });

        return future;
    }
}
