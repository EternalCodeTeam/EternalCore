package com.eternalcode.core.feature.punishment.history;

import static com.eternalcode.core.feature.punishment.database.PunishmentTable.CREATED_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.KIND_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.TARGET_UUID_COLUMN;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentKind;
import com.eternalcode.core.feature.punishment.Revocation;
import com.eternalcode.core.feature.punishment.database.PunishmentMapper;
import com.eternalcode.core.feature.punishment.database.PunishmentRevocations;
import com.eternalcode.core.feature.punishment.database.PunishmentTable;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
class PunishmentHistoryRepositoryOrmLite extends AbstractRepositoryOrmLite implements PunishmentHistoryRepository {

    private static final boolean DESCENDING = false;

    private final PunishmentMapper mapper;

    @Inject
    private PunishmentHistoryRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler, PunishmentMapper mapper) throws SQLException {
        super(databaseManager, scheduler);
        this.mapper = mapper;

        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PunishmentTable.class);
    }

    @Override
    public CompletableFuture<List<Punishment>> findByTarget(UUID targetUuid, Set<PunishmentKind> kinds, int page, int pageSize) {
        this.validateQuery(kinds, page, pageSize);

        return this.action(PunishmentTable.class, dao -> {
            QueryBuilder<PunishmentTable, Object> builder = this.newestFirst(dao, page, pageSize);
            Where<PunishmentTable, Object> where = builder.where();
            where.and(
                where.eq(TARGET_UUID_COLUMN, targetUuid),
                where.in(KIND_COLUMN, kinds)
            );

            return this.toPunishments(dao, where.query());
        });
    }

    @Override
    public CompletableFuture<List<Punishment>> findRecent(Set<PunishmentKind> kinds, int page, int pageSize) {
        this.validateQuery(kinds, page, pageSize);

        return this.action(PunishmentTable.class, dao -> {
            QueryBuilder<PunishmentTable, Object> builder = this.newestFirst(dao, page, pageSize);
            builder.where().in(KIND_COLUMN, kinds);

            return this.toPunishments(dao, builder.query());
        });
    }

    private QueryBuilder<PunishmentTable, Object> newestFirst(Dao<PunishmentTable, Object> dao, int page, int pageSize)
        throws SQLException {
        return dao.queryBuilder()
            .orderBy(CREATED_AT_COLUMN, DESCENDING)
            .offset((long) page * pageSize)
            .limit((long) pageSize);
    }

    private List<Punishment> toPunishments(Dao<PunishmentTable, Object> dao, List<PunishmentTable> rows) throws SQLException {
        Set<PunishmentKind> revocationKinds = rows.stream()
            .map(PunishmentTable::kind)
            .filter(PunishmentKind::isRevocable)
            .map(PunishmentKind::revocation)
            .collect(Collectors.toSet());

        Map<UUID, Revocation> revocations = PunishmentRevocations.load(dao, revocationKinds, rows);

        return rows.stream()
            .map(row -> this.mapper.rowToPunishment(row, revocations.get(row.id())))
            .toList();
    }

    private void validateQuery(Set<PunishmentKind> kinds, int page, int pageSize) {
        if (kinds.isEmpty()) {
            throw new IllegalArgumentException("kinds cannot be empty");
        }
        if (kinds.stream().anyMatch(PunishmentKind::isRevocation)) {
            throw new IllegalArgumentException("kinds cannot contain revocations, they are merged into punishments: " + kinds);
        }
        if (page < 0) {
            throw new IllegalArgumentException("page cannot be negative, got " + page);
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be positive, got " + pageSize);
        }
    }
}
