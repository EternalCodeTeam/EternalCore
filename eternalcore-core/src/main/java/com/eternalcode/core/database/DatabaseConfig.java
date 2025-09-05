package com.eternalcode.core.database;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
@Getter
@Accessors(fluent = true)
public class DatabaseConfig extends OkaeriConfig implements DatabaseSettings {

    @Comment({"Type of the database driver (e.g., SQLITE, H2, MYSQL, MARIADB, POSTGRESQL).", "Determines the "
        + "database type "
        + "to be used."})
    public DatabaseDriverType databaseType = DatabaseDriverType.MYSQL;

    @Comment({"Hostname of the database server.", "For local databases, this is usually 'localhost'."})
    public String hostname = "localhost";

    @Comment({"Port number of the database server. Common ports:",
              " - MySQL: 3306",
              " - PostgreSQL: 5432",
              " - H2: Not applicable (file-based)",
              " - SQLite: Not applicable (file-based)"})
    public int port = 3306;

    @Comment("Name of the database to connect to. This is the name of the specific database instance.")
    public String database = "eternalcore";

    @Comment("Username for the database connection. This is the user account used to authenticate with the database.")
    public String username = "root";

    @Comment("Password for the database connection. This is the password for the specified user account.")
    public String password = "password";

    @Comment("Enable SSL for the database connection. Set to true to use SSL/TLS for secure connections.")
    public boolean ssl = false;

    @Comment("Connection pool size. This determines the maximum number of connections in the pool.")
    public int poolSize = 16;

    @Comment("Connection timeout in milliseconds. This is the maximum time to wait for a connection from the pool.")
    public int timeout = 30000;
}
