package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.Duration;
import java.util.UUID;

@FeatureDocs(
    name = "ChatManager",
    description = "It allows you to manage chat, with slowmode, chat clear, chat on/off etc.",
    permission = { "eternalcore.chat.noslowmode", "eternalcore.chat.bypass" }
)
@Controller
public class ChatManagerController implements Listener {

    private final ChatManager chatManager;
    private final NoticeService noticeService;

    @Inject
    public ChatManagerController(ChatManager chatManager, NoticeService noticeService) {
        this.chatManager = chatManager;
        this.noticeService = noticeService;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChatSlowMode(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!this.chatManager.getChatSettings().isChatEnabled() && !player.hasPermission("enernalcore.chat.bypass")) {
            this.noticeService.create()
                .notice(translation -> translation.chat().disabledChatInfo())
                .player(player.getUniqueId())
                .send();

            event.setCancelled(true);
            return;
        }

        UUID uuid = player.getUniqueId();

        if (this.chatManager.hasSlowedChat(uuid) && !player.hasPermission("enernalcore.chat.noslowmode")) {
            Duration time = this.chatManager.getSlowDown(uuid);

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
    public void markUseChat(AsyncPlayerChatEvent event) {
        this.chatManager.markUseChat(event.getPlayer().getUniqueId());
    }

}
