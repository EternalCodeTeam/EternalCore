package com.eternalcode.core.configuration.migration;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import java.util.logging.Logger;

@Controller
class MigrationController implements Subscriber {

    private final MigrationService migrationService;
    private final ConfigurationManager configurationManager;
    private final Logger logger;

    @Inject
    MigrationController(MigrationService migrationService, ConfigurationManager configurationManager, Logger logger) {
        this.migrationService = migrationService;
        this.configurationManager = configurationManager;
        this.logger = logger;
    }

    @Subscribe
    void onMigration(EternalInitializeEvent event) {
        for (ReloadableConfig config : configurationManager.getConfigs()) {
            boolean wasMigrated = migrationService.migrate(config);

            if (wasMigrated) {
                configurationManager.save(config);
                logger.info("Configuration " + config.getClass().getSimpleName() + " was migrated and saved.");
            }
        }
    }

}
