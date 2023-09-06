package com.eternalcode.core.teleport;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.EventListener;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@EventListener
public class TeleportListeners implements Listener {

    private final NoticeService noticeService;
    private final TeleportTaskService teleportTaskService;

    @Inject
    public TeleportListeners(NoticeService noticeService, TeleportTaskService teleportTaskService) {
        this.noticeService = noticeService;
        this.teleportTaskService = teleportTaskService;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            this.removeTeleport(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        this.removeTeleport(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.removeTeleport(player);
    }

    private void removeTeleport(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.teleportTaskService.inTeleport(uuid)) {
            this.teleportTaskService.removeTeleport(uuid);

            this.noticeService.create()
                    .notice(translation -> translation.teleport().teleportTaskCanceled())
                    .send();
        }
    }

}
