package com.eternalcode.core.feature.afk;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class AfkController implements Listener {

    private final AfkService afkService;

    public AfkController(AfkService afkService) {
        this.afkService = afkService;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onMove(PlayerMoveEvent event) {
        UUID uniqueId = event.getPlayer().getUniqueId();

        this.afkService.updateInteraction(uniqueId);
        this.afkService.updateLastPlayerMovement(uniqueId);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onQuit(PlayerQuitEvent event) {
        this.afkService.clearAfk(event.getPlayer().getUniqueId());
    }


}
