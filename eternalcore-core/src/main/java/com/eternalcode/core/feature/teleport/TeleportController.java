package com.eternalcode.core.feature.teleport;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.eternalcode.core.feature.teleport.apiteleport.TeleportResult;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Controller
class TeleportController implements Listener {

    private final TeleportTaskService teleportTaskService;

    @Inject
    TeleportController(TeleportTaskService teleportTaskService) {
        this.teleportTaskService = teleportTaskService;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            UUID playerUUID = player.getUniqueId();

            this.teleportTaskService.cancelTeleport(playerUUID, TeleportResult.CANCELLED_DAMAGE);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        this.teleportTaskService.cancelTeleport(playerUUID, TeleportResult.CANCELLED_WORLD_CHANGE);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        this.teleportTaskService.cancelTeleport(playerUUID, TeleportResult.CANCELLED_KICK);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        this.teleportTaskService.cancelTeleport(playerUUID, TeleportResult.CANCELLED_QUIT);
    }
}
