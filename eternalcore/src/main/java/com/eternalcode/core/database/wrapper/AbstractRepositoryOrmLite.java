package com.eternalcode.core.database.wrapper;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.scheduler.Scheduler;
import com.j256.ormlite.dao.Dao;
import panda.std.function.ThrowingFunction;
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.util.List;

abstract class AbstractRepositoryOrmLite {

    protected final DatabaseManager databaseManager;
    protected final Scheduler scheduler;

    protected AbstractRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) {
        this.databaseManager = databaseManager;
        this.scheduler = scheduler;
    }

    <T> Completable<Dao.CreateOrUpdateStatus> save(Class<T> type, T warp) {
        return this.action(type, dao -> dao.createOrUpdate(warp));
    }

    <T> Completable<T> saveIfNotExist(Class<T> type, T warp) {
        return this.action(type, dao -> dao.createIfNotExists(warp));
    }

    <T, ID> Completable<T> select(Class<T> type, ID id) {
        return this.action(type, dao -> dao.queryForId(id));
    }

    <T> Completable<Integer> delete(Class<T> type, T warp) {
        return this.action(type, dao -> dao.delete(warp));
    }

    <T, ID> Completable<Integer> deleteById(Class<T> type, ID id) {
        return this.action(type, dao -> dao.deleteById(id));
    }

    <T> Completable<List<T>> selectAll(Class<T> type) {
        return this.action(type, Dao::queryForAll);
    }

    <T, ID, R> Completable<R> action(Class<T> type, ThrowingFunction<Dao<T, ID>, R, SQLException> action) {
        Completable<R> completableFuture = new Completable<>();

        this.scheduler.async(() -> {
            Dao<T, ID> dao = this.databaseManager.getDao(type);

            try {
                completableFuture.complete(action.apply(dao));
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        return completableFuture;
    }

}
