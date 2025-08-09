package com.eternalcode.core.database;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import java.io.File;
import java.util.logging.Logger;

@Setup
class DatabaseManagerSetup {

    @Bean
    DatabaseManager databaseManager(DatabaseSettings databaseSettings, Logger logger, File dataFolder) {
        DatabaseManager databaseManager = new DatabaseManager(logger, dataFolder, databaseSettings);

        databaseManager.connect();
        return databaseManager;
    }

    @Subscribe(EternalShutdownEvent.class)
    void onShutdown(DatabaseManager databaseManager) {
        databaseManager.close();
    }
}
