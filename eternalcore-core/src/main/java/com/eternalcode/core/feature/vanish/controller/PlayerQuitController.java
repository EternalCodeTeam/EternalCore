package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Controller
class PlayerQuitController implements Listener {

    private final VanishService vanishService;

    @Inject
    PlayerQuitController(VanishService vanishService) {
        this.vanishService = vanishService;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.vanishService.isVanished(player)) {
            event.setQuitMessage(null);

            this.vanishService.disableVanish(player);
        }
    }
}
