package com.eternalcode.core.database;

import static com.eternalcode.core.database.DatabaseConnectionDriverConstant.*;

public enum DatabaseDriverType {

    MY_SQL(MYSQL_DRIVER, MYSQL_JDBC_URL),
    MARIA_DB(MARIADB_DRIVER, MARIADB_JDBC_URL),
    POSTGRE_SQL(POSTGRESQL_DRIVER, POSTGRESQL_JDBC_URL),
    H2(H2_DRIVER, H2_JDBC_URL),
    SQLITE(SQLITE_DRIVER, SQLITE_JDBC_URL);

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
