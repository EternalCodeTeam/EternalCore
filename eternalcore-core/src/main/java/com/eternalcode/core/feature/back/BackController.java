package com.eternalcode.core.feature.back;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.injector.annotations.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BackController implements Listener {

    private final BackService backService;

    @Inject
    public BackController(BackService backService) {
        this.backService = backService;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player entity = event.getEntity();

        this.backService.markDeathLocation(entity.getUniqueId(), PositionAdapter.convert(entity.getLocation()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) {
            return;
        }

        this.backService.markTeleportLocation(event.getPlayer().getUniqueId(), PositionAdapter.convert(event.getFrom()));
    }
}
