package com.eternalcode.core.database;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.google.common.base.Stopwatch;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Server;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DatabaseManager {

    private final PluginConfiguration config;
    private final EternalCore eternalCore;

    private final File dataFolder;
    private final Map<Class<?>, Dao<?, ?>> cachedDao = new ConcurrentHashMap<>();

    private HikariDataSource dataSource;
    private ConnectionSource connectionSource;

    public DatabaseManager(PluginConfiguration config, EternalCore eternalCore, File dataFolder) {
        this.config = config;
        this.eternalCore = eternalCore;
        this.dataFolder = dataFolder;
    }

    public void connect() throws SQLException {
        Stopwatch stopwatch = Stopwatch.createStarted();

        DatabaseType databaseType = config.database.databaseType;

        this.dataSource = new HikariDataSource();

        this.dataSource.addDataSourceProperty("cachePrepStmts", true);
        this.dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.dataSource.addDataSourceProperty("useServerPrepStmts", true);

        this.dataSource.setMaximumPoolSize(5);
        this.dataSource.setUsername(this.config.database.username);
        this.dataSource.setPassword(this.config.database.password);

        switch (DatabaseType.valueOf(databaseType.toString().toUpperCase())) {

            case MYSQL -> {
                this.dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                this.dataSource.setJdbcUrl("jdbc:mysql://" + this.config.database.hostname + ":" + this.config.database.port + "/" + this.config.database.database);
            }

            case MARIADB -> {
                this.dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
                this.dataSource.setJdbcUrl("jdbc:mariadb://" + this.config.database.hostname + ":" + this.config.database.port + "/" + this.config.database.database);
            }

            case H2 -> {
                this.dataSource.setDriverClassName("org.h2.Driver");
                this.dataSource.setJdbcUrl("jdbc:h2:./" + this.dataFolder + "/database");
            }

            case SQLITE -> {
                this.dataSource.setDriverClassName("org.sqlite.JDBC");
                this.dataSource.setJdbcUrl("jdbc:sqlite:" + this.dataFolder + "/database.db");
            }

            case POSTGRESQL -> {
                this.dataSource.setDriverClassName("org.postgresql.Driver");
                this.dataSource.setJdbcUrl("jdbc:postgresql://" + this.config.database.hostname + ":" + this.config.database.port + "/");
            }

            default -> throw new SQLException("SQL type '" + databaseType + "' not found");
        }

        this.connectionSource = new DataSourceConnectionSource(dataSource, dataSource.getJdbcUrl());
        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        this.eternalCore.getLogger().info("Loaded database " + databaseType.toString().toLowerCase() + " in " + millis + "ms");

    }

    public void close() {
        try {
            this.dataSource.close();
            this.connectionSource.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T, ID> Dao<T, ID> getDao(Class<T> type) {
        try {
            Dao<?, ?> dao = cachedDao.get(type);

            if (dao == null) {
                dao = DaoManager.createDao(this.connectionSource, type);
                this.cachedDao.put(type, dao);
            }

            return (Dao<T, ID>) dao;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
