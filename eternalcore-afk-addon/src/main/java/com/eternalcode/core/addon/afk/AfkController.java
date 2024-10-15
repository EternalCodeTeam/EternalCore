package com.eternalcode.core.addon.afk;

import com.eternalcode.core.feature.afk.AfkService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class AfkController implements Listener {

    private final AfkHologramService afkHologramService;
    private final AfkService afkService;

    public AfkController(AfkHologramService afkHologramService, AfkService afkService) {
        this.afkHologramService = afkHologramService;
        this.afkService = afkService;
    }

    @EventHandler
    void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (this.afkService.isAfk(uuid)) {
            this.afkHologramService.deleteHologram(uuid);
        }
    }
}
