package com.eternalcode.core.database.jooq;

import com.eternalcode.core.database.DatabaseDriverType;
import org.jooq.SQLDialect;

final class JooqDialectMapper {

    private JooqDialectMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static SQLDialect map(DatabaseDriverType driverType) {
        return switch (driverType) {
            case MYSQL -> SQLDialect.MYSQL;
            case MARIADB -> SQLDialect.MARIADB;
            case POSTGRESQL -> SQLDialect.POSTGRES;
            case H2 -> SQLDialect.H2;
            case SQLITE -> SQLDialect.SQLITE;
        };
    }
}
