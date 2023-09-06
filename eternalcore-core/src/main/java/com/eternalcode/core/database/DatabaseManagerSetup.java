package com.eternalcode.core.database;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EternalCoreShutdownEvent;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@BeanSetup
@Controller
class DatabaseManagerSetup implements Subscriber {

    @Bean
    public DatabaseManager getDatabaseManager(PluginConfiguration config, Logger logger, File dataFolder) {
        DatabaseManager databaseManager = new DatabaseManager(config, logger, dataFolder);

        try {
            databaseManager.connect();
        }
        catch (SQLException sqlException) {
            logger.log(Level.SEVERE, "Can not connect to database! Some functions may not work!", sqlException);
            throw new RuntimeException(sqlException); //TODO handle this exception and create fake database see NoneRepository
        }

        return databaseManager;
    }

    @Subscribe
    public void onShutdown(EternalCoreShutdownEvent event, Logger logger, DatabaseManager databaseManager) {
        databaseManager.close();
    }

}
