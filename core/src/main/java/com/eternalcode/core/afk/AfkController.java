package com.eternalcode.core.afk;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AfkController implements Listener {

    private final AfkService afkService;

    public AfkController(AfkService afkService) {
        this.afkService = afkService;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onMove(PlayerMoveEvent event) {
        this.handleEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onQuit(PlayerQuitEvent event) {
        this.afkService.clearAfk(event.getPlayer().getUniqueId());
    }

    private void handleEvent(PlayerEvent event) {
        Player player = event.getPlayer();

        if (!this.afkService.isAfk(player.getUniqueId())) {
            return;
        }

        this.afkService.markInteraction(player.getUniqueId());
    }

}
