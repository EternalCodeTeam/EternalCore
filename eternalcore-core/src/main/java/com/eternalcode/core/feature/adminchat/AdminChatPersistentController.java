package com.eternalcode.core.feature.adminchat;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.adminchat.event.AdminChatEvent;
import com.eternalcode.core.feature.adminchat.event.AdminChatService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Controller
class AdminChatPersistentController implements Listener {

    private final AdminChatService adminChatService;
    private final EventCaller eventCaller;

    @Inject
    AdminChatPersistentController(
        AdminChatService adminChatService,
        EventCaller eventCaller
    ) {
        this.adminChatService = adminChatService;
        this.eventCaller = eventCaller;
    }

    @EventHandler
    void onAdminChat (AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!this.adminChatService.hasPersistentChat(player.getUniqueId())) {
            return;
        }
        event.setCancelled(true);

        AdminChatEvent adminChatEvent = this.eventCaller.callEvent(new AdminChatEvent(player, message));
        if (adminChatEvent.isCancelled()) {
            return;
        }

        String eventMessage = adminChatEvent.getContent();

        this.adminChatService.sendAdminChatMessage(eventMessage, player.getName());
    }
}
