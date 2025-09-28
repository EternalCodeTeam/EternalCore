package com.eternalcode.core.bridge.dynmap;

import com.eternalcode.core.bridge.BridgeInitializer;
import com.eternalcode.core.feature.vanish.VanishService;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

public class DynmapBridgeInitializer implements BridgeInitializer {

    private final VanishService vanishService;
    private final JavaPlugin plugin;

    public DynmapBridgeInitializer(VanishService vanishService, JavaPlugin plugin) {
        this.vanishService = vanishService;
        this.plugin = plugin;
    }

    @Override
    public void initialize() {
        Plugin dynmapPlugin = this.plugin.getServer().getPluginManager().getPlugin("dynmap");

        if (!(dynmapPlugin instanceof DynmapAPI dynmapAPI)) {
            this.plugin.getLogger().warning("Dynmap plugin found, but it's not a valid DynmapAPI instance. Dynmap bridge will not be initialized.");
            return;
        }

        DynmapBridgeController controller = new DynmapBridgeController(this.vanishService, dynmapAPI);
        this.plugin.getServer().getPluginManager().registerEvents(controller, this.plugin);
    }
}
