package com.eternalcode.core.feature.chat;

import static com.eternalcode.core.feature.chat.ChatSlowModeController.CHAT_BYPASS_PERMISSION;
import static com.eternalcode.core.feature.chat.ChatSlowModeController.CHAT_SLOWMODE_BYPASS_PERMISSION;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.chat.event.restrict.ChatRestrictCause;
import com.eternalcode.core.feature.chat.event.restrict.ChatRestrictEvent;
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

@PermissionDocs(
    name = "Bypass chat slowmode or disabled chat",
    description = "Allows you to bypass chat restrictions such as slowmode or disabled chat.",
    permission = {CHAT_SLOWMODE_BYPASS_PERMISSION, CHAT_BYPASS_PERMISSION}
)
@Controller
class ChatSlowModeController implements Listener {

    static final String CHAT_SLOWMODE_BYPASS_PERMISSION = "eternalcore.chat.noslowmode";
    static final String CHAT_BYPASS_PERMISSION = "eternalcore.chat.bypass";

    private final ChatService chatService;
    private final ChatSettings chatSettings;
    private final NoticeService noticeService;
    private final EventCaller eventCaller;

    @Inject
    ChatSlowModeController(
        ChatService chatService,
        ChatSettings chatSettings,
        NoticeService noticeService,
        EventCaller eventCaller
    ) {
        this.chatService = chatService;
        this.chatSettings = chatSettings;
        this.noticeService = noticeService;
        this.eventCaller = eventCaller;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onChatSlowMode(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        UUID uniqueId = player.getUniqueId();

        if (!this.chatSettings.chatEnabled() && !player.hasPermission(CHAT_BYPASS_PERMISSION)) {
            this.noticeService.create()
                .notice(translation -> translation.chat().chatDisabledInfo())
                .player(uniqueId)
                .send();

            ChatRestrictEvent chatRestrictEvent =
                this.eventCaller.callEvent(new ChatRestrictEvent(uniqueId, ChatRestrictCause.CHAT_DISABLED));

            if (!chatRestrictEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
        }

        if (this.chatService.hasSlowedChat(uniqueId) && !player.hasPermission(CHAT_SLOWMODE_BYPASS_PERMISSION)) {
            ChatRestrictEvent restrictEvent = new ChatRestrictEvent(uniqueId, ChatRestrictCause.SLOWMODE);
            ChatRestrictEvent chatRestrictEvent = this.eventCaller.callEvent(restrictEvent);

            if (!chatRestrictEvent.isCancelled()) {
                Duration remainingDuration = this.chatService.getRemainingSlowDown(uniqueId);

                this.noticeService.create()
                    .player(uniqueId)
                    .notice(translation -> translation.chat().slowModeNotReady())
                    .placeholder("{TIME}", DurationUtil.format(remainingDuration, true))
                    .send();

                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void markUseChat(AsyncPlayerChatEvent event) {
        this.chatService.markUseChat(event.getPlayer().getUniqueId());
    }
}
