package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Controller
// TODO: if-check
public class ChatController implements Listener {

    private final VanishService vanishService;

    @Inject
    public ChatController(VanishService vanishService) {
        this.vanishService = vanishService;
    }

    @EventHandler
    void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }
}
