package com.eternalcode.core.bridge.dynmap;

import com.eternalcode.core.bridge.BridgeInitializer;
import com.eternalcode.core.feature.vanish.VanishService;
import org.bukkit.plugin.java.JavaPlugin;

public class DynmapBridgeInitializer implements BridgeInitializer {

    private final VanishService vanishService;
    private final JavaPlugin plugin;

    public DynmapBridgeInitializer(VanishService vanishService, JavaPlugin plugin) {
        this.vanishService = vanishService;
        this.plugin = plugin;
    }

    @Override
    public void initialize() {
        DynmapBridgeController controller = new DynmapBridgeController(vanishService, plugin);
        plugin.getServer().getPluginManager().registerEvents(controller, plugin);
    }
}
