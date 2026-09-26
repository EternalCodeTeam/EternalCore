package com.eternalcode.core.feature.punishment.database;

import static com.eternalcode.core.feature.punishment.database.PunishmentTable.EXPIRES_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.REVOKED_AT_COLUMN;

import com.j256.ormlite.stmt.Where;
import java.sql.SQLException;

final class PunishmentConditions {

    private PunishmentConditions() {
    }

    static Where<PunishmentTable, Object> active(Where<PunishmentTable, Object> where, long nowEpochMillis)
        throws SQLException {
        return where.and(
            where.isNull(REVOKED_AT_COLUMN),
            notExpired(where, nowEpochMillis)
        );
    }

    static Where<PunishmentTable, Object> notExpired(Where<PunishmentTable, Object> where, long nowEpochMillis)
        throws SQLException {
        return where.or(
            where.isNull(EXPIRES_AT_COLUMN),
            where.gt(EXPIRES_AT_COLUMN, nowEpochMillis)
        );
    }
}
