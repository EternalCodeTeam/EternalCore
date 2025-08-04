package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.vanish.VanishConfig;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.injector.annotations.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

@Controller
class FoodController implements Listener {

    private final VanishService vanishService;
    private final VanishConfig config;

    @Inject
    FoodController(VanishService vanishService, PluginConfiguration pluginConfiguration) {
        this.vanishService = vanishService;
        this.config = pluginConfiguration.vanish;
    }

    @EventHandler
    void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!this.config.blockHungerLoss) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (this.vanishService.isVanished(player)) {
            event.setCancelled(true);
        }
    }
}
