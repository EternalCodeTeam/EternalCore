package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@FeatureDocs(
    name = "ChatManager",
    description = "It allows you to manage chat, with slowmode, chat clear, chat on/off etc.",
    permission = { "eternalcore.chat.noslowmode", "eternalcore.chat.bypass" }
)
@Controller
class ChatManagerController implements Listener {

    private static final String CHAT_SLOWMODE_BYPASS_PERMISSION = "eternalcore.chat.noslowmode";
    private static final String CHAT_BYPASS_PERMISSION = "eternalcore.chat.bypass";

    private final ChatService chatService;
    private final ChatSettings chatSettings;
    private final NoticeService noticeService;

    @Inject
    ChatManagerController(ChatService chatService, ChatSettings chatSettings, NoticeService noticeService) {
        this.chatService = chatService;
        this.chatSettings = chatSettings;
        this.noticeService = noticeService;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onChatSlowMode(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!this.chatSettings.isChatEnabled() && !player.hasPermission(CHAT_BYPASS_PERMISSION)) {
            this.noticeService.create()
                .notice(translation -> translation.chat().disabledChatInfo())
                .player(player.getUniqueId())
                .send();

            event.setCancelled(true);
            return;
        }

        UUID uuid = player.getUniqueId();

        if (this.chatService.hasSlowedChat(uuid) && !player.hasPermission(CHAT_SLOWMODE_BYPASS_PERMISSION)) {
            Duration time = this.chatService.getSlowDown(uuid);

            this.noticeService
                .create()
                .player(player.getUniqueId())
                .notice(translation -> translation.chat().slowMode())
                .placeholder("{TIME}", DurationUtil.format(time))
                .send();

            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void markUseChat(AsyncPlayerChatEvent event) {
        this.chatService.markUseChat(event.getPlayer().getUniqueId());
    }
}
