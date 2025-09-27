package com.eternalcode.core.bridge;

import com.eternalcode.core.bridge.dynmap.DynmapBridgeInitializer;
import com.eternalcode.core.bridge.placeholderapi.PlaceholderApiExtension;
import com.eternalcode.core.bridge.placeholderapi.PlaceholderApiReplacer;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

class BridgeManager {

    private final PlaceholderRegistry placeholderRegistry;
    private final PluginDescriptionFile pluginDescriptionFile;
    private final Server server;
    private final Logger logger;
    private final JavaPlugin plugin;
    private final VanishService vanishService;

    BridgeManager(PlaceholderRegistry placeholderRegistry,
        PluginDescriptionFile pluginDescriptionFile,
        Server server,
        Logger logger,
        JavaPlugin plugin,
        VanishService vanishService
    ) {
        this.placeholderRegistry = placeholderRegistry;
        this.pluginDescriptionFile = pluginDescriptionFile;
        this.server = server;
        this.logger = logger;
        this.plugin = plugin;
        this.vanishService = vanishService;
    }

    void init() {
        this.setupBridge("PlaceholderAPI", () -> {
            this.placeholderRegistry.registerPlaceholder(new PlaceholderApiReplacer());
            new PlaceholderApiExtension(this.placeholderRegistry, this.pluginDescriptionFile).initialize();
        });

        this.setupBridge("dynmap", () -> {
            new DynmapBridgeInitializer(this.vanishService, this.plugin).initialize();
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
