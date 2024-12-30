package com.eternalcode.core.database;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.publish.Subscribe;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

@Setup
class DatabaseManagerSetup {

    @Bean
    DatabaseManager databaseManager(PluginConfiguration pluginConfiguration, Logger logger, File dataFolder) {
        DatabaseManager databaseManager = new DatabaseManager(pluginConfiguration, logger, dataFolder);

        try {
            databaseManager.connect();
        }
        catch (SQLException exception) {
            logger.severe("Could not connect to database! Some functions may not work properly!");
            throw new RuntimeException(exception);
        }

        return databaseManager;
    }

    @Subscribe(EternalShutdownEvent.class)
    void onShutdown(DatabaseManager databaseManager) {
        databaseManager.close();
    }

}
