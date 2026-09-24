package com.eternalcode.core.feature.punishment.database;

import static com.eternalcode.core.feature.punishment.database.PunishmentTable.EXPIRES_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.ID_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.REVOKED_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.TARGET_UUID_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.TYPE_COLUMN;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentType;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
class PunishmentRepositoryOrmLite extends AbstractRepositoryOrmLite implements PunishmentRepository {

    @Inject
    private PunishmentRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PunishmentTable.class);
    }

    @Override
    public CompletableFuture<Void> save(Punishment punishment) {
        return this.saveIfNotExist(PunishmentTable.class, PunishmentTable.from(punishment)).thenApply(result -> null);
    }

    @Override
    public CompletableFuture<Void> deactivate(UUID punishmentId) {
        return this.action(PunishmentTable.class, dao -> {
            UpdateBuilder<PunishmentTable, Object> builder = dao.updateBuilder();
            builder.updateColumnValue(REVOKED_AT_COLUMN, this.nowEpochMillis());
            builder.where()
                .eq(ID_COLUMN, punishmentId)
                .and()
                .isNull(REVOKED_AT_COLUMN);
            return builder.update();
        }).thenApply(updatedRows -> null);
    }

    @Override
    public CompletableFuture<Optional<Punishment>> findActive(UUID targetUuid, PunishmentType type) {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TARGET_UUID_COLUMN, targetUuid),
                where.eq(TYPE_COLUMN, type),
                this.active(where, this.nowEpochMillis())
            );
            return Optional.ofNullable(where.queryForFirst()).map(PunishmentTable::toPunishment);
        });
    }

    @Override
    public CompletableFuture<List<Punishment>> findActive(UUID targetUuid) {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TARGET_UUID_COLUMN, targetUuid),
                this.active(where, this.nowEpochMillis())
            );
            return this.toPunishments(where.query());
        });
    }

    @Override
    public CompletableFuture<List<Punishment>> findExpired(Instant now) {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.isNull(REVOKED_AT_COLUMN)
                .and()
                .isNotNull(EXPIRES_AT_COLUMN)
                .and()
                .le(EXPIRES_AT_COLUMN, now.toEpochMilli());
            return this.toPunishments(where.query());
        });
    }

    @Override
    public CompletableFuture<List<Punishment>> findAllActive(PunishmentType type) {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TYPE_COLUMN, type),
                this.active(where, this.nowEpochMillis())
            );
            return this.toPunishments(where.query());
        });
    }

    @Override
    public CompletableFuture<List<Punishment>> findAllUnexpired(PunishmentType type, Instant now) {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TYPE_COLUMN, type),
                this.notExpired(where, now.toEpochMilli())
            );
            return this.toPunishments(where.query());
        });
    }

    @Override
    public CompletableFuture<Integer> countByTargetAndType(UUID targetUuid, PunishmentType type, Instant now) {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TYPE_COLUMN, type),
                where.eq(TARGET_UUID_COLUMN, targetUuid),
                this.notExpired(where, now.toEpochMilli())
            );
            return Math.toIntExact(where.countOf());
        });
    }

    private Where<PunishmentTable, Object> active(Where<PunishmentTable, Object> where, long nowEpochMillis)
        throws SQLException {
        return where.and(
            where.isNull(REVOKED_AT_COLUMN),
            this.notExpired(where, nowEpochMillis)
        );
    }

    private Where<PunishmentTable, Object> notExpired(Where<PunishmentTable, Object> where, long nowEpochMillis)
        throws SQLException {
        return where.or(
            where.isNull(EXPIRES_AT_COLUMN),
            where.gt(EXPIRES_AT_COLUMN, nowEpochMillis)
        );
    }

    private List<Punishment> toPunishments(List<PunishmentTable> tables) {
        return tables.stream()
            .map(PunishmentTable::toPunishment)
            .toList();
    }

    private long nowEpochMillis() {
        return Instant.now().toEpochMilli();
    }
}
