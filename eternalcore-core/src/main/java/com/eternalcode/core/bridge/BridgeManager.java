package com.eternalcode.core.bridge;

import com.eternalcode.core.bridge.placeholderapi.PlaceholderApiExtension;
import com.eternalcode.core.bridge.placeholderapi.PlaceholderApiReplacer;
import com.eternalcode.core.feature.automessage.AutoMessageService;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

public class BridgeManager {

    private final PlaceholderRegistry placeholderRegistry;
    private final Server server;
    private final Logger logger;

    public BridgeManager(PlaceholderRegistry placeholderRegistry, Server server, Logger logger) {
        this.placeholderRegistry = placeholderRegistry;
        this.server = server;
        this.logger = logger;
    }

    public void init() {
        this.setupBridge("PlaceholderAPI", () -> {
            this.placeholderRegistry.registerPlaceholder(new PlaceholderApiReplacer());
            new PlaceholderApiExtension(this.placeholderRegistry).initialize();
        });
    }

    private void setupBridge(String pluginName, BridgeInitializer bridge) {
        PluginManager pluginManager = this.server.getPluginManager();

        if (pluginManager.isPluginEnabled(pluginName)) {
            bridge.initialize();

            this.logger.info("Successfully hooked into " + pluginName + " bridge!");
        }
    }
}
