package com.eternalcode.core.database;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EternalCoreShutdownEvent;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

@Subscriber
@BeanSetup
class DatabaseManagerSetup {

    @Bean
    DatabaseManager databaseManager(PluginConfiguration pluginConfiguration, Logger logger, File dataFolder) {
        DatabaseManager databaseManager = new DatabaseManager(pluginConfiguration, logger, dataFolder);

        try {
            databaseManager.connect();
        }
        catch (SQLException exception) {
            logger.severe("Can not connect to database! Some functions may not work!");
            throw new RuntimeException(exception);
        }

        return databaseManager;
    }

    @Subscribe(EternalCoreShutdownEvent.class)
    void onShutdown(DatabaseManager databaseManager) {
        databaseManager.close();
    }

}
