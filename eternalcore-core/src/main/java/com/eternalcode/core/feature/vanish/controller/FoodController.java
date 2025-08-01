package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishConfiguration;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.injector.annotations.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

@Controller
public class FoodController implements Listener {

    private final VanishService vanishService;
    private final VanishConfiguration config;

    @Inject
    public FoodController(VanishService vanishService, VanishConfiguration config) {
        this.vanishService = vanishService;
        this.config = config;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!this.config.blockFoodConsumption) {
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
