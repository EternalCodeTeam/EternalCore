package com.eternalcode.core.feature.punishment;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.time.OffsetDateTime;

final class PunishmentSchema {

    static final Table<?> PUNISHMENTS = DSL.table("eternalcore_punishments");

    static final Field<String> ID = DSL.field("id", SQLDataType.CHAR(36).nullable(false));
    static final Field<String> TARGET_UUID = DSL.field("target_uuid", SQLDataType.CHAR(36).nullable(false));
    static final Field<String> TARGET_NAME = DSL.field("target_name", SQLDataType.VARCHAR(16).nullable(false));
    static final Field<String> OPERATOR_UUID = DSL.field("operator_uuid", SQLDataType.CHAR(36).nullable(false));
    static final Field<String> OPERATOR_NAME = DSL.field("operator_name", SQLDataType.VARCHAR(16).nullable(false));
    static final Field<String> TYPE = DSL.field("type", SQLDataType.VARCHAR(16).nullable(false));
    static final Field<String> REASON = DSL.field("reason", SQLDataType.VARCHAR(255).nullable(false));
    static final Field<OffsetDateTime> CREATED_AT = DSL.field("created_at", SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false));
    static final Field<OffsetDateTime> EXPIRES_AT = DSL.field("expires_at", SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(true));
    static final Field<Boolean> ACTIVE = DSL.field("active", SQLDataType.BOOLEAN.nullable(false));

    private PunishmentSchema() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
