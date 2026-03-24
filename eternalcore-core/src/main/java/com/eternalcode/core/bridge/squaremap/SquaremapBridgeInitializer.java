package com.eternalcode.core.bridge.squaremap;

import com.eternalcode.core.bridge.BridgeInitializer;
import com.eternalcode.core.feature.vanish.VanishService;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.jpenilla.squaremap.api.SquaremapProvider;

public class SquaremapBridgeInitializer implements BridgeInitializer {

    private final VanishService vanishService;
    private final JavaPlugin plugin;

    public SquaremapBridgeInitializer(VanishService vanishService, JavaPlugin plugin) {
        this.vanishService = vanishService;
        this.plugin = plugin;
    }

    @Override
    public void initialize() {
        try {
            SquaremapBridgeController controller = new SquaremapBridgeController(
                this.vanishService,
                SquaremapProvider.get().playerManager()
            );
            this.plugin.getServer().getPluginManager().registerEvents(controller, this.plugin);
        } catch (IllegalStateException exception) {
            this.plugin.getLogger().warning(
                "Squaremap plugin found, but could not obtain API instance. Squaremap bridge will not be initialized."
            );
        }
    }
}
