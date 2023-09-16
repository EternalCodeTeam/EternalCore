package com.eternalcode.core.feature.automessage;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.feature.automessage.AutoMessageRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.scheduler.Scheduler;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
class AutoMessageRepositoryOrmLite extends AbstractRepositoryOrmLite implements AutoMessageRepository {

    @Inject
    private AutoMessageRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), AutoMessageIgnoreWrapper.class);
    }

    @Override
    public Completable<Set<UUID>> findReceivers(Set<UUID> onlineUniqueIds) {
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

        return wrapperList.thenApply(ignores -> {
            Set<UUID> ignoredIds = ignores.stream()
                .map(wrapper -> wrapper.uniqueId)
                .collect(Collectors.toSet());

            Set<UUID> onlineUniqueIdsWithoutIngores = new HashSet<>();

            for (UUID onlineUniqueId : onlineUniqueIds) {
                if (!ignoredIds.contains(onlineUniqueId)) {
                    onlineUniqueIdsWithoutIngores.add(onlineUniqueId);
                }
            }

            return onlineUniqueIdsWithoutIngores;
        });
    }

    @Override
    public Completable<Boolean> isReceiving(UUID uniqueId) {
        return this.selectSafe(AutoMessageIgnoreWrapper.class, uniqueId).thenApply(Optional::isEmpty);
    }

    @Override
    public Completable<Boolean> switchReceiving(UUID uniqueId) {
        return this.selectSafe(AutoMessageIgnoreWrapper.class, uniqueId).thenCompose(optional -> {
            if (optional.isEmpty()) {
                return this.save(AutoMessageIgnoreWrapper.class, new AutoMessageIgnoreWrapper(uniqueId)).thenApply(result -> false);
            }

            AutoMessageIgnoreWrapper wrapper = optional.get();

            return this.delete(AutoMessageIgnoreWrapper.class, wrapper).thenApply(state -> true);
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

}
