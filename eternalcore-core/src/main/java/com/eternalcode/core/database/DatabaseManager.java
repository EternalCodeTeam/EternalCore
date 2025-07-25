package com.eternalcode.core.database;

import com.google.common.base.Stopwatch;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DatabaseManager {

    private final Logger logger;
    private final File dataFolder;
    private final DatabaseSettings settings;
    private final Map<Class<?>, Dao<?, ?>> cachedDao = new ConcurrentHashMap<>();
    private HikariDataSource dataSource;
    private ConnectionSource connectionSource;

    public DatabaseManager(Logger logger, File dataFolder, DatabaseSettings settings) {
        this.logger = logger;
        this.dataFolder = dataFolder;
        this.settings = settings;
    }

    public void connect() {
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();

            this.dataSource = new HikariDataSource();
            this.dataSource.addDataSourceProperty("cachePrepStmts", "true");
            this.dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
            this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            this.dataSource.addDataSourceProperty("useServerPrepStmts", "true");

            this.dataSource.setMaximumPoolSize(settings.poolSize());
            this.dataSource.setConnectionTimeout(settings.timeout());
            this.dataSource.setUsername(settings.getUsername());
            this.dataSource.setPassword(settings.getPassword());

            DatabaseDriverType type = settings.getDriverType();
            this.dataSource.setDriverClassName(type.getDriver());

            String jdbcUrl = switch (type) {
                case H2, SQLITE -> type.formatUrl(dataFolder);
                case POSTGRE_SQL -> type.formatUrl(settings.getHostname(), settings.getPort(), settings.getDatabase(), settings.isSSL());
                default -> type.formatUrl(settings.getHostname(), settings.getPort(), settings.getDatabase(), settings.isSSL(), settings.isSSL());
            };

            this.dataSource.setJdbcUrl(jdbcUrl);

            this.connectionSource = new DataSourceConnectionSource(this.dataSource, jdbcUrl);

            this.logger.info("Loaded database " + type + " in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        }
        catch (Exception exception) {
            throw new DatabaseException("Failed to connect to the database", exception);
        }
    }

    public void close() {
        try {
            this.dataSource.close();
            this.connectionSource.close();
        }
        catch (Exception exception) {
            this.logger.severe("Failed to close database connection: " + exception.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public <T, ID> Dao<T, ID> getDao(Class<T> type) {
        return (Dao<T, ID>) this.cachedDao.computeIfAbsent(type, clazz -> {
            try {
                return DaoManager.createDao(this.connectionSource, clazz);
            } catch (SQLException e) {
                throw new DatabaseException("Failed to create DAO for " + clazz.getName(), e);
            }
        });
    }

    public ConnectionSource connectionSource() {
        return this.connectionSource;
    }
}
