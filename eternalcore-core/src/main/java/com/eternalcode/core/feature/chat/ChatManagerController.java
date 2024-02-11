package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.shared.time.DurationParser;
import com.eternalcode.commons.shared.time.TemporalAmountParser;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
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

    private static final TemporalAmountParser<Duration> TEMPORAL_AMOUNT_PARSER = DurationParser.DATE_TIME_UNITS;
    private final ChatManager chatManager;
    private final NoticeService noticeService;

    @Inject
    ChatManagerController(ChatManager chatManager, NoticeService noticeService) {
        this.chatManager = chatManager;
        this.noticeService = noticeService;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onChatSlowMode(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!this.chatManager.getChatSettings().isChatEnabled() && !player.hasPermission("eternalcore.chat.bypass")) {
            this.noticeService.create()
                .notice(translation -> translation.chat().disabledChatInfo())
                .player(player.getUniqueId())
                .send();

            event.setCancelled(true);
            return;
        }

        UUID uuid = player.getUniqueId();

        if (this.chatManager.hasSlowedChat(uuid) && !player.hasPermission("eternalcore.chat.noslowmode")) {
            Duration time = this.chatManager.getSlowDown(uuid);

            this.noticeService
                .create()
                .player(player.getUniqueId())
                .notice(translation -> translation.chat().slowMode())
                .placeholder("{TIME}", TEMPORAL_AMOUNT_PARSER.format(time))
                .send();

            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void markUseChat(AsyncPlayerChatEvent event) {
        this.chatManager.markUseChat(event.getPlayer().getUniqueId());
    }
}
