package com.eternalcode.core.bridge;

import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;

@BeanSetup
class BridgeManagerInitializer {

    @Bean
    BridgeManager bridgeManager(PlaceholderRegistry registry, Server server, Logger logger, PluginDescriptionFile description) {
        BridgeManager bridgeManager = new BridgeManager(registry, description, server, logger);
        bridgeManager.init();

        return bridgeManager;
    }

}
