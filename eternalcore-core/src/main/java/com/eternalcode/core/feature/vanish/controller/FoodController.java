package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

@Controller
class FoodController implements Listener {

    private final VanishService vanishService;
    private final VanishSettings settings;

    @Inject
    FoodController(VanishService vanishService, VanishSettings settings) {
        this.vanishService = vanishService;
        this.settings = settings;
    }

    @EventHandler
    void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!this.settings.blockHungerLoss()) {
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
