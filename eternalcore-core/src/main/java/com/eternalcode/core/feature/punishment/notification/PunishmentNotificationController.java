package com.eternalcode.core.feature.punishment.notification;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Controller
class PunishmentNotificationController implements Listener {

    private final PunishmentNotificationServiceImpl notificationService;

    @Inject
    PunishmentNotificationController(PunishmentNotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        this.notificationService.load(event.getPlayer());
    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        this.notificationService.unload(event.getPlayer().getUniqueId());
    }
}
