package com.eternalcode.core.feature.teleport.controller;

import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@Controller
class TeleportDeathLastLocationController implements Listener {

    private final TeleportService teleportService;

    @Inject
    TeleportDeathLastLocationController(TeleportService teleportService) {
        this.teleportService = teleportService;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        this.teleportService.markLastLocation(player.getUniqueId(), player.getLocation());
    }

}
