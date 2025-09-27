package com.eternalcode.core.bridge;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

@Setup
class BridgeManagerInitializer {

    @Bean
    BridgeManager bridgeManager(
        PlaceholderRegistry registry, Server server, Logger logger,
        PluginDescriptionFile description, JavaPlugin plugin, VanishService vanishService) {
        BridgeManager bridgeManager = new BridgeManager(registry, description, server, logger, plugin, vanishService);
        bridgeManager.init();

        return bridgeManager;
    }
}
