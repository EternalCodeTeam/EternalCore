package com.eternalcode.core.teleport;

import com.eternalcode.core.chat.notification.NoticeService;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class TeleportListeners implements Listener {

    private final NoticeService noticeService;
    private final TeleportManager teleportManager;

    public TeleportListeners(NoticeService noticeService, TeleportManager teleportManager) {
        this.noticeService = noticeService;
        this.teleportManager = teleportManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMove(final PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX()
            && event.getFrom().getBlockY() == event.getTo().getBlockY()
            && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
            return;
        }
        final Player player = event.getPlayer();

        this.removeTeleport(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            this.removeTeleport(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onKick(final PlayerKickEvent event) {
        Player player = event.getPlayer();

        this.removeTeleport(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onQuit(final PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.removeTeleport(player);
    }

    private void removeTeleport(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.teleportManager.inTeleport(uuid)) {
            this.teleportManager.removeTeleport(uuid);

            player.sendActionBar(Component.text());

            this.noticeService.notice()
                .message(messages -> messages.teleport().cancel())
                .send();
        }
    }

}
