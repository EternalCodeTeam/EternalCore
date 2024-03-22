package com.eternalcode.core.feature.teleport.controller;

import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@Controller
class TeleportController implements Listener {

    private final NoticeService noticeService;
    private final TeleportTaskService teleportTaskService;

    @Inject
    TeleportController(NoticeService noticeService, TeleportTaskService teleportTaskService) {
        this.noticeService = noticeService;
        this.teleportTaskService = teleportTaskService;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            this.removeTeleport(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        this.removeTeleport(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.removeTeleport(player);
    }

    private void removeTeleport(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.teleportTaskService.isInTeleport(uuid)) {
            this.teleportTaskService.removeTeleport(uuid);

            this.noticeService.create()
                    .notice(translation -> translation.teleport().teleportTaskCanceled())
                    .send();
        }
    }

}
