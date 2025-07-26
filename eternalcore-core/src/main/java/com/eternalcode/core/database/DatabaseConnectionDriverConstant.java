package com.eternalcode.core.database;

final class DatabaseConnectionDriverConstant {

    private DatabaseConnectionDriverConstant() {}

    // mysql
    static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String MYSQL_JDBC_URL = "jdbc:mysql://%s:%s/%s?sslMode=%s";

    // mariadb — accepts "true"/"false" as valid sslMode values (aliases for "verify-full" and "disable")
    static final String MARIADB_DRIVER = "org.mariadb.jdbc.Driver";
    static final String MARIADB_JDBC_URL = "jdbc:mariadb://%s:%s/%s?sslMode=%s";

    // h2
    static final String H2_DRIVER = "org.h2.Driver";
    static final String H2_JDBC_URL = "jdbc:h2:./%s/database";

    // sqlite
    static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    static final String SQLITE_JDBC_URL = "jdbc:sqlite:%s/database.db";

    // postgresql
    static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    static final String POSTGRESQL_JDBC_URL = "jdbc:postgresql://%s:%s/%s?sslmode=%s";

    static String sslParamForMySQL(boolean enabled) {
        return enabled ? "REQUIRED" : "DISABLED";
    }

    static String sslParamForPostgreSQL(boolean enabled) {
        return enabled ? "require" : "disable";
    }
}
