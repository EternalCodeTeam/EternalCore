package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Controller
class ChatController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;
    private final VanishSettings settings;

    @Inject
    ChatController(NoticeService noticeService, VanishService vanishService, VanishSettings settings) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
        this.settings = settings;
    }

    @EventHandler
    void onChat(AsyncPlayerChatEvent event) {
        if (!this.settings.blockChatUsage()) {
            return;
        }

        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);

        this.noticeService.player(player.getUniqueId(), message -> message.vanish().cantUseChatWhileVanished());
    }
}
