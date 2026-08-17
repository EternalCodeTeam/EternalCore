package com.eternalcode.core.bridge.squaremap;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.jpenilla.squaremap.api.Squaremap;

public class SquaremapBridgeController implements Listener {

    private final VanishService vanishService;
    private final Squaremap squaremap;

    public SquaremapBridgeController(VanishService vanishService, Squaremap squaremap) {
        this.vanishService = vanishService;
        this.squaremap = squaremap;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onVanish(EnableVanishEvent event) {
        Player player = event.getPlayer();
        this.squaremap.playerManager().hide(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onReappear(DisableVanishEvent event) {
        Player player = event.getPlayer();
        this.squaremap.playerManager().show(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.vanishService.isVanished(player)) {
            this.squaremap.playerManager().hide(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (this.vanishService.isVanished(player)) {
            this.squaremap.playerManager().hide(player.getUniqueId());
        }
    }
}
