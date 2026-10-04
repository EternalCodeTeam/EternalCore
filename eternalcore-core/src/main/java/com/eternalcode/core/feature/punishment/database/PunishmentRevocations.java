package com.eternalcode.core.feature.punishment.database;

import static com.eternalcode.core.feature.punishment.database.PunishmentTable.CREATED_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.KIND_COLUMN;

import com.eternalcode.core.feature.punishment.Revocation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Resolves which punishment rows were revoked later by a REVOKED_* row.
 */
final class PunishmentRevocations {

    private PunishmentRevocations() {
    }

    /**
     * @return revocations of the given punishments keyed by the id of the revoked punishment
     */
    static Map<UUID, Revocation> load(
        Dao<PunishmentTable, Object> dao,
        Collection<PunishmentKind> revocationKinds,
        Collection<PunishmentTable> punishments
    ) throws SQLException {
        if (punishments.isEmpty() || revocationKinds.isEmpty()) {
            return Map.of();
        }

        long earliestCreatedAt = punishments.stream()
            .mapToLong(PunishmentTable::createdAtMillis)
            .min()
            .orElseThrow();

        Where<PunishmentTable, Object> where = dao.queryBuilder().where();
        where.and(
            where.in(KIND_COLUMN, revocationKinds),
            where.ge(CREATED_AT_COLUMN, earliestCreatedAt)
        );

        Map<UUID, Revocation> revocations = new HashMap<>();

        for (PunishmentTable revocationRow : where.query()) {
            revocations.put(revocationRow.revokedId(), revocationRow.toRevocation());
        }

        return revocations;
    }
}
