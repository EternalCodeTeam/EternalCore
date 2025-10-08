package com.eternalcode.core.feature.adminchat;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

@Controller
final class AdminChatChannelController implements Listener {

    private final AdminChatService adminChatService;
    private final Scheduler scheduler;

    @Inject
    AdminChatChannelController(@NotNull AdminChatService adminChatService, Scheduler scheduler) {
        this.adminChatService = adminChatService;
        this.scheduler = scheduler;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    void onPlayerChat(@NotNull AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!this.adminChatService.hasEnabledChat(player.getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        this.scheduler.run(() -> this.adminChatService.sendAdminChatMessage(event.getMessage(), player));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.adminChatService.hasEnabledChat(player.getUniqueId())) {
            this.adminChatService.disableChat(player.getUniqueId());
        }
    }
}
