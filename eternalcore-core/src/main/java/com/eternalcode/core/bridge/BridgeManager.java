package com.eternalcode.core.bridge;

import com.eternalcode.core.bridge.placeholderapi.PlaceholderApiReplacer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

@Service
public class BridgeManager {

    private final PlaceholderRegistry placeholderRegistry;
    private final Server server;
    private final Logger logger;

    @Inject
    public BridgeManager(PlaceholderRegistry placeholderRegistry, Server server, Logger logger) {
        this.placeholderRegistry = placeholderRegistry;
        this.server = server;
        this.logger = logger;
    }

    void init() {
        this.setupBridge("PlaceholderAPI", () -> this.placeholderRegistry.registerPlayerPlaceholderReplacer(new PlaceholderApiReplacer()));
    }

    private void setupBridge(String pluginName, BridgeInitializer bridge) {
        PluginManager pluginManager = this.server.getPluginManager();

        if (pluginManager.isPluginEnabled(pluginName)) {
            bridge.initialize();

            this.logger.info("Successfully hooked into " + pluginName + " bridge!");
        }
    }
}
