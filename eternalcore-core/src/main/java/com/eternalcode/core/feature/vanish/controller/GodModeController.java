package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Controller
//TODO: if-check
public class GodModeController implements Listener {

    @EventHandler
    void onEnable(EnableVanishEvent event) {
        Player player = event.getPlayer();

        player.setInvulnerable(true);
    }

    @EventHandler
    void onDisable(DisableVanishEvent event) {
        Player player = event.getPlayer();

        player.setInvulnerable(false);
    }
}
