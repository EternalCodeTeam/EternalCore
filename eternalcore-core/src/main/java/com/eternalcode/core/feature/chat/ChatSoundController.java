package com.eternalcode.core.feature.chat;

import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Controller
class ChatSoundController implements Listener {

    private final NoticeService noticeService;
    private final ChatSettings settings;

    ChatSoundController(NoticeService noticeService, ChatSettings settings) {
        this.noticeService = noticeService;
        this.settings = settings;
    }

    @EventHandler
    void onSound(AsyncPlayerChatEvent event) {
        this.noticeService.create()
            .notice(this.settings.chatNotice())
            .onlinePlayers()
            .send();
    }

}
