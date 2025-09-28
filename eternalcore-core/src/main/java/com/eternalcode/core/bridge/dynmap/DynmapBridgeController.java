package com.eternalcode.core.bridge.dynmap;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.dynmap.DynmapAPI;

public class DynmapBridgeController implements Listener {

    private final VanishService vanishService;
    private final DynmapAPI dynmapAPI;

    public DynmapBridgeController(VanishService vanishService, DynmapAPI dynmapAPI) {
        this.vanishService = vanishService;
        this.dynmapAPI = dynmapAPI;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onVanish(EnableVanishEvent event) {
        if (this.dynmapAPI == null) {
            return;
        }

        Player player = event.getPlayer();
        this.dynmapAPI.setPlayerVisiblity(player, false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onReappear(DisableVanishEvent event) {
        if (this.dynmapAPI == null) {
            return;
        }

        Player player = event.getPlayer();
        this.dynmapAPI.setPlayerVisiblity(player, true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        if (this.dynmapAPI == null) {
            return;
        }

        Player player = event.getPlayer();
        if (this.vanishService.isVanished(player)) {
            this.dynmapAPI.setPlayerVisiblity(player, false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        if (this.dynmapAPI == null) {
            return;
        }

        Player player = event.getPlayer();
        if (this.vanishService.isVanished(player)) {
            this.dynmapAPI.setPlayerVisiblity(player, false);
        }
    }
}
