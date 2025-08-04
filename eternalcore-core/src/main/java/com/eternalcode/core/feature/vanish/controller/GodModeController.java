package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Controller
class GodModeController implements Listener {

    private final VanishSettings config;

    @Inject
    GodModeController(VanishSettings config) {
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    void onEnable(EnableVanishEvent event) {
        if (!this.config.godMode()) {
            return;
        }
        Player player = event.getPlayer();

        player.setInvulnerable(true);
    }

    @EventHandler(ignoreCancelled = true)
    void onDisable(DisableVanishEvent event) {
        if (!this.config.godMode()) {
            return;
        }
        Player player = event.getPlayer();

        player.setInvulnerable(false);
    }
}
