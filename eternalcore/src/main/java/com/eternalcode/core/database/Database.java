package com.eternalcode.core.database;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.google.common.base.Stopwatch;
import com.zaxxer.hikari.HikariDataSource;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Database {
    private final ConfigurationManager configurationManager;
    private final Logger logger;
    private HikariDataSource hikari;

    public Database(ConfigurationManager configurationManager, Logger logger) {
        this.configurationManager = configurationManager;
        this.logger = logger;
    }

    public void connect() {
        Stopwatch started = Stopwatch.createStarted();
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        this.hikari = new HikariDataSource();

        this.hikari.setDataSourceClassName(String.valueOf(config.database.databaseType));
        this.hikari.addDataSourceProperty("serverName", config.database.databaseHost);
        this.hikari.addDataSourceProperty("port", config.database.databasePort);
        this.hikari.addDataSourceProperty("databaseName", config.database.databaseName);
        this.hikari.addDataSourceProperty("user", config.database.databaseUser);
        this.hikari.addDataSourceProperty("password", config.database.databasePassword);

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.logger.info("Connected to database in " + millis + "ms");
    }

    public void disconnect() {
        this.hikari.close();
    }
}
