package com.eternalcode.core.teleport;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TeleportDeathController implements Listener {

    private final TeleportService teleportService;

    public TeleportDeathController(TeleportService teleportService) {
        this.teleportService = teleportService;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        this.teleportService.markLastLocation(player.getUniqueId(), player.getLocation());
    }

}
