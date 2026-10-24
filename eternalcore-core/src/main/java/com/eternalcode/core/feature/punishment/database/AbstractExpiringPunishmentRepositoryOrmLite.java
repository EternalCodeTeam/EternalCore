package com.eternalcode.core.feature.punishment.database;

import static com.eternalcode.core.feature.punishment.database.PunishmentTable.KIND_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.TARGET_UUID_COLUMN;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentType;
import com.eternalcode.core.feature.punishment.Revocation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Shared storage logic of every revocable domain. A subclass only says which {@link PunishmentType}
 * it owns and how its domain object maps to a row.
 */
public abstract class AbstractExpiringPunishmentRepositoryOrmLite<T extends ExpirablePunishment>
    extends AbstractRepositoryOrmLite
    implements ExpiringPunishmentRepository<T> {

    private final PunishmentType kind;
    private final PunishmentType revocationKind;
    private final Function<T, PunishmentTable> toRow;
    private final BiFunction<PunishmentTable, Revocation, T> fromRow;

    protected AbstractExpiringPunishmentRepositoryOrmLite(
        DatabaseManager databaseManager,
        Scheduler scheduler,
        PunishmentType kind,
        Function<T, PunishmentTable> toRow,
        BiFunction<PunishmentTable, Revocation, T> fromRow
    ) throws SQLException {
        super(databaseManager, scheduler);

        this.kind = kind;
        this.revocationKind = kind.revocation();
        this.toRow = toRow;
        this.fromRow = fromRow;

        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PunishmentTable.class);
    }

    @Override
    public void save(T punishment) {
        this.query(PunishmentTable.class, dao -> dao.createIfNotExists(this.toRow.apply(punishment)));
    }

    @Override
    public boolean revoke(UUID punishmentId, PunishmentActor revokedBy, Instant revokedAt) {
        return this.query(PunishmentTable.class, dao -> {
            PunishmentTable original = dao.queryForId(punishmentId);

            if (original == null || original.kind() != this.kind) {
                return false;
            }

            Instant expiresAt = original.expiresAt();

            if (expiresAt != null && !expiresAt.isAfter(revokedAt)) {
                return false;
            }

            Map<UUID, Revocation> revocations = PunishmentRevocations.load(dao, Set.of(this.revocationKind), List.of(original));

            if (revocations.containsKey(punishmentId)) {
                return false;
            }

            dao.create(PunishmentTable.revocationOf(original, revokedBy, revokedAt));
            return true;
        });
    }

    @Override
    public List<T> findActive(UUID targetUuid) {
        return this.query(PunishmentTable.class, dao -> this.queryActive(dao, targetUuid));
    }

    @Override
    public List<T> findAllActive() {
        return this.query(PunishmentTable.class, dao -> this.queryActive(dao, null));
    }

    /**
     * @param targetUuid null = every target
     */
    private List<T> queryActive(Dao<PunishmentTable, Object> dao, UUID targetUuid) throws SQLException {
        Instant now = Instant.now();
        Where<PunishmentTable, Object> where = dao.queryBuilder().where();

        if (targetUuid == null) {
            where.eq(KIND_COLUMN, this.kind);
        }
        else {
            where.and(
                where.eq(KIND_COLUMN, this.kind),
                where.eq(TARGET_UUID_COLUMN, targetUuid)
            );
        }

        // expiration lives in the JSON details, so it is checked here instead of in SQL
        List<PunishmentTable> rows = where.query().stream()
            .filter(row -> row.expiresAt() == null || row.expiresAt().isAfter(now))
            .toList();
        Map<UUID, Revocation> revocations = PunishmentRevocations.load(dao, Set.of(this.revocationKind), rows);

        return rows.stream()
            .filter(row -> !revocations.containsKey(row.id()))
            .map(row -> this.fromRow.apply(row, null))
            .toList();
    }
}
