package com.eternalcode.core.bridge.squaremap;

import com.eternalcode.core.bridge.BridgeInitializer;
import com.eternalcode.core.feature.vanish.VanishService;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.jpenilla.squaremap.api.Squaremap;

public class SquaremapBridgeInitializer implements BridgeInitializer {

    private final VanishService vanishService;
    private final JavaPlugin plugin;

    public SquaremapBridgeInitializer(VanishService vanishService, JavaPlugin plugin) {
        this.vanishService = vanishService;
        this.plugin = plugin;
    }

    @Override
    public void initialize() {
        Squaremap squaremap = this.plugin.getServer().getServicesManager().load(Squaremap.class);

        if (squaremap == null) {
            this.plugin.getLogger().warning("Squaremap plugin found, but its API service is not available. Squaremap bridge will not be initialized.");
            return;
        }

        SquaremapBridgeController controller = new SquaremapBridgeController(this.vanishService, squaremap);
        this.plugin.getServer().getPluginManager().registerEvents(controller, this.plugin);
    }
}
