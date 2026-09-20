package com.eternalcode.core.feature.punishment.ip;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.time.OffsetDateTime;

final class IpPunishmentSchema {

    static final Table<?> PUNISHMENT_IPS = DSL.table("eternalcore_punishment_ips");

    static final Field<String> ID = DSL.field("id", SQLDataType.CHAR(36).nullable(false));
    static final Field<String> IP_CIPHERTEXT = DSL.field("ip_ciphertext", SQLDataType.VARCHAR(255).nullable(false));
    static final Field<String> IP_IV = DSL.field("ip_iv", SQLDataType.VARCHAR(64).nullable(false));
    static final Field<String> IP_HASH = DSL.field("ip_hash", SQLDataType.CHAR(64).nullable(false));
    static final Field<String> TARGET_UUID = DSL.field("target_uuid", SQLDataType.CHAR(36).nullable(false));
    static final Field<String> TARGET_NAME = DSL.field("target_name", SQLDataType.VARCHAR(16).nullable(false));
    static final Field<String> OPERATOR_UUID = DSL.field("operator_uuid", SQLDataType.CHAR(36).nullable(false));
    static final Field<String> OPERATOR_NAME = DSL.field("operator_name", SQLDataType.VARCHAR(16).nullable(false));
    static final Field<String> REASON = DSL.field("reason", SQLDataType.VARCHAR(255).nullable(false));
    static final Field<OffsetDateTime> CREATED_AT = DSL.field("created_at", SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false));
    static final Field<OffsetDateTime> EXPIRES_AT = DSL.field("expires_at", SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(true));
    static final Field<OffsetDateTime> REVOKED_AT = DSL.field("revoked_at", SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(true));

    private IpPunishmentSchema() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
