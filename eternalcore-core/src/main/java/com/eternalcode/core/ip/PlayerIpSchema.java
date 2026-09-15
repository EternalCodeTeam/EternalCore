package com.eternalcode.core.ip;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.time.OffsetDateTime;

final class PlayerIpSchema {

    static final Table<?> PLAYER_IPS = DSL.table("eternalcore_player_ips");

    static final Field<String> ID = DSL.field("id", SQLDataType.CHAR(36).nullable(false));
    static final Field<String> IP_CIPHERTEXT = DSL.field("ip_ciphertext", SQLDataType.VARCHAR(255).nullable(false));
    static final Field<String> IP_IV = DSL.field("ip_iv", SQLDataType.VARCHAR(64).nullable(false));
    static final Field<String> IP_HASH = DSL.field("ip_hash", SQLDataType.CHAR(64).nullable(false));
    static final Field<String> TARGET_UUID = DSL.field("target_uuid", SQLDataType.CHAR(36).nullable(false));
    static final Field<String> TARGET_NAME = DSL.field("target_name", SQLDataType.VARCHAR(16).nullable(false));
    static final Field<OffsetDateTime> FIRST_SEEN = DSL.field("first_seen", SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false));
    static final Field<OffsetDateTime> LAST_SEEN = DSL.field("last_seen", SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false));

    private PlayerIpSchema() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
