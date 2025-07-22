package com.eternalcode.core.feature.adminchat;

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

    @Inject
    AdminChatPersistentController(AdminChatService adminChatService) {
        this.adminChatService = adminChatService;
    }

    @EventHandler(ignoreCancelled = true)
    void onAdminChat (AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!this.adminChatService.hasEnabledChat(player.getUniqueId())) {
            return;
        }

        event.setCancelled(true);
        this.adminChatService.sendAdminChatMessage(event.getMessage(), player);
    }
}
