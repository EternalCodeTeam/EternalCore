package com.eternalcode.core.database.wrapper;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.automessage.AutoMessageRepository;
import com.eternalcode.core.scheduler.Scheduler;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AutoMessageRepositoryOrmLite extends AbstractRepositoryOrmLite implements AutoMessageRepository {

    private AutoMessageRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) {
        super(databaseManager, scheduler);
    }

    @Override
    public Completable<List<UUID>> findRecivers(List<UUID> onlineUniqueIds) {
        if (onlineUniqueIds.isEmpty()) {
            return Completable.completed(onlineUniqueIds);
        }

        Completable<List<AutoMessageIgnoreWrapper>> wrapperList = this.action(AutoMessageIgnoreWrapper.class, dao -> {
            Where<AutoMessageIgnoreWrapper, Object> where = dao.queryBuilder().where();

            for (UUID onlineUniqueId : onlineUniqueIds) {
                where.eq("unique_id", onlineUniqueId);
            }

            where.or(onlineUniqueIds.size());

            return where.query();
        });

        return wrapperList.thenApply(ignores -> ignores.stream()
            .map(wrapper -> wrapper.uniqueId)
            .toList());
    }

    @Override
    public Completable<Boolean> isReciving(UUID uniqueId) {
        return this.selectSafe(AutoMessageIgnoreWrapper.class, uniqueId).thenApply(Optional::isEmpty);
    }

    @Override
    public Completable<Boolean> switchReciving(UUID uniqueId) {
        return this.selectSafe(AutoMessageIgnoreWrapper.class, uniqueId).thenCompose(optional -> {
            if (optional.isEmpty()) {
                return this.save(AutoMessageIgnoreWrapper.class, new AutoMessageIgnoreWrapper(uniqueId)).thenApply(result -> true);
            }

            AutoMessageIgnoreWrapper wrapper = optional.get();

            return this.delete(AutoMessageIgnoreWrapper.class, wrapper).thenApply(state -> false);
        });
    }

    @DatabaseTable(tableName = "eternal_core_auto_message_ignore")
    private static class AutoMessageIgnoreWrapper {

        @DatabaseField(columnName = "unique_id", id = true)
        UUID uniqueId;

        AutoMessageIgnoreWrapper() {}

        AutoMessageIgnoreWrapper(UUID uniqueId) {
            this.uniqueId = uniqueId;
        }
    }

    public static AutoMessageRepositoryOrmLite create(DatabaseManager databaseManager, Scheduler scheduler) {
        try {
            TableUtils.createTableIfNotExists(databaseManager.connectionSource(), AutoMessageIgnoreWrapper.class);
        }
        catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return new AutoMessageRepositoryOrmLite(databaseManager, scheduler);
    }
}
