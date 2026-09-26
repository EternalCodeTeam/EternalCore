package com.eternalcode.core.feature.punishment.database;

import static com.eternalcode.core.feature.punishment.database.PunishmentTable.CREATED_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.ID_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.REVOKED_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.REVOKED_BY_NAME_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.REVOKED_BY_UUID_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.TARGET_UUID_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.TYPE_COLUMN;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
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

    private static final boolean DESCENDING = false;

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
    public CompletableFuture<Boolean> revoke(UUID punishmentId, PunishmentTarget revokedBy, Instant revokedAt) {
        long revokedAtEpochMillis = revokedAt.toEpochMilli();

        return this.action(PunishmentTable.class, dao -> {
            UpdateBuilder<PunishmentTable, Object> builder = dao.updateBuilder();
            builder.updateColumnValue(REVOKED_AT_COLUMN, revokedAtEpochMillis);
            builder.updateColumnValue(REVOKED_BY_UUID_COLUMN, revokedBy.uuid());
            builder.updateColumnValue(REVOKED_BY_NAME_COLUMN, revokedBy.name());

            Where<PunishmentTable, Object> where = builder.where();
            where.and(
                where.eq(ID_COLUMN, punishmentId),
                PunishmentConditions.active(where, revokedAtEpochMillis)
            );
            return builder.update() > 0;
        });
    }

    @Override
    public CompletableFuture<Optional<Punishment>> findActive(UUID targetUuid, PunishmentType type) {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TARGET_UUID_COLUMN, targetUuid),
                where.eq(TYPE_COLUMN, type),
                PunishmentConditions.active(where, this.nowEpochMillis())
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
                where.ne(TYPE_COLUMN, PunishmentType.KICK),
                PunishmentConditions.active(where, this.nowEpochMillis())
            );
            return this.toPunishments(where.query());
        });
    }

    @Override
    public CompletableFuture<List<Punishment>> findAllActive(PunishmentType type) {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TYPE_COLUMN, type),
                PunishmentConditions.active(where, this.nowEpochMillis())
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
                PunishmentConditions.notExpired(where, now.toEpochMilli())
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
                PunishmentConditions.notExpired(where, now.toEpochMilli())
            );
            return Math.toIntExact(where.countOf());
        });
    }

    @Override
    public CompletableFuture<List<Punishment>> findByTarget(UUID targetUuid, int page, int pageSize) {
        this.validatePagination(page, pageSize);

        return this.action(PunishmentTable.class, dao -> this.toPunishments(dao.queryBuilder()
            .orderBy(CREATED_AT_COLUMN, DESCENDING)
            .offset((long) page * pageSize)
            .limit((long) pageSize)
            .where()
            .eq(TARGET_UUID_COLUMN, targetUuid)
            .query()));
    }

    @Override
    public CompletableFuture<List<Punishment>> findRecent(int page, int pageSize) {
        this.validatePagination(page, pageSize);

        return this.action(PunishmentTable.class, dao -> this.toPunishments(dao.queryBuilder()
            .orderBy(CREATED_AT_COLUMN, DESCENDING)
            .offset((long) page * pageSize)
            .limit((long) pageSize)
            .query()));
    }

    private void validatePagination(int page, int pageSize) {
        if (page < 0) {
            throw new IllegalArgumentException("page cannot be negative, got " + page);
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be positive, got " + pageSize);
        }
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
