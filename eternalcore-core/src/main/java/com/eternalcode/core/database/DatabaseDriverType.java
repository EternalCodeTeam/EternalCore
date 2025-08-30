package com.eternalcode.core.database;

import static com.eternalcode.core.database.DatabaseConnectionDriverConstant.*;

public enum DatabaseDriverType {

    MYSQL(MYSQL_DRIVER, MYSQL_JDBC_URL),
    MARIADB(MARIADB_DRIVER, MARIADB_JDBC_URL),
    POSTGRESQL(POSTGRESQL_DRIVER, POSTGRESQL_JDBC_URL),
    H2(H2_DRIVER, H2_JDBC_URL),
    SQLITE(SQLITE_DRIVER, SQLITE_JDBC_URL),

    H2_TEST(H2_DRIVER, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MYSQL");

    private final String driver;
    private final String urlFormat;

    DatabaseDriverType(String driver, String urlFormat) {
        this.driver = driver;
        this.urlFormat = urlFormat;
    }

    public String getDriver() {
        return driver;
    }

    public String formatUrl(Object... args) {
        return String.format(urlFormat, args);
    }
}
