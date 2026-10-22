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
            try {
                future.complete(this.query(type, action));
            }
            catch (DatabaseException exception) {
                future.completeExceptionally(exception);
            }
        });

        return future;
    }

    /**
     * Runs the action on the calling thread and blocks until the database answers.
     * Call it only from an already asynchronous context, never from the main thread.
     */
    protected <T, ID, R> R query(
        Class<T> type,
        ThrowingFunction<Dao<T, ID>, R, SQLException> action
    ) {
        Dao<T, ID> dao;

        try {
            dao = this.databaseManager.getDao(type);
        }
        catch (Exception exception) {
            throw new DatabaseException("Failed to get DAO for class: " + type.getName(), exception);
        }

        try {
            return action.apply(dao);
        }
        catch (Throwable throwable) {
            throw new DatabaseException("Database action failed for: " + type.getSimpleName(), throwable);
        }
    }
}
