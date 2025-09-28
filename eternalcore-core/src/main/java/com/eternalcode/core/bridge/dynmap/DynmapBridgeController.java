package com.eternalcode.core.bridge.dynmap;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

public class DynmapBridgeController implements Listener {

    private final VanishService vanishService;
    private final DynmapAPI dynmapAPI;

    public DynmapBridgeController(VanishService vanishService, JavaPlugin plugin) {
        this.vanishService = vanishService;
        Plugin dynmapPlugin = plugin.getServer().getPluginManager().getPlugin("dynmap");
        this.dynmapAPI = (DynmapAPI) dynmapPlugin;
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
