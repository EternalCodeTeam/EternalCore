package com.eternalcode.core.bridge;

import com.eternalcode.core.bridge.placeholderapi.PlaceholderApiExtension;
import com.eternalcode.core.bridge.placeholderapi.PlaceholderApiReplacer;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

public class BridgeManager {

    private final PlaceholderRegistry placeholderRegistry;
    private final Plugin plugin;
    private final Logger logger;

    public BridgeManager(PlaceholderRegistry placeholderRegistry, Plugin plugin, Logger logger) {
        this.placeholderRegistry = placeholderRegistry;
        this.plugin = plugin;
        this.logger = logger;
    }

    public void init() {
        this.setupBridge("PlaceholderAPI", () -> {
            this.placeholderRegistry.registerPlaceholder(new PlaceholderApiReplacer());
            new PlaceholderApiExtension(this.placeholderRegistry, this.plugin.getDescription()).initialize();
        });
    }

    private void setupBridge(String pluginName, BridgeInitializer bridge) {
        PluginManager pluginManager = this.plugin.getServer().getPluginManager();

        if (pluginManager.isPluginEnabled(pluginName)) {
            bridge.initialize();

            this.logger.info("Successfully hooked into " + pluginName + " bridge!");
        }
    }
}
