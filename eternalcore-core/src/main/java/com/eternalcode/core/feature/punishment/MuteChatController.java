package com.eternalcode.core.feature.punishment;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Controller
@PermissionDocs(
    name = "Mute Bypass",
    permission = PunishmentPermissions.MUTE_BYPASS,
    description = "Permission allows to bypass being muted"
)
class MuteChatController implements Listener {

    private final PunishmentSettings punishmentSettings;
    private final PunishmentService punishmentService;
    private final NoticeService noticeService;

    @Inject
    MuteChatController(PunishmentSettings punishmentSettings, PunishmentService punishmentService, NoticeService noticeService) {
        this.punishmentSettings = punishmentSettings;
        this.punishmentService = punishmentService;
        this.noticeService = noticeService;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        UUID uniqueId = player.getUniqueId();
        Optional<Punishment> activeMute = this.punishmentService.getActiveMute(uniqueId);

        if (activeMute.isEmpty()) {
            return;
        }

        Punishment punishment = activeMute.get();

        String remainingText = punishment.isPermanent()
            ? this.punishmentSettings.permanentLabel()
            : DurationUtil.format(Duration.between(Instant.now(), punishment.expiresAt().orElseThrow()), true);

        this.noticeService.create()
            .notice(translation -> translation.punishment().muteBlockedChat())
            .placeholder("{REASON}", punishment.reason())
            .placeholder("{REMAINING_TIME}", remainingText)
            .player(uniqueId)
            .send();

        event.setCancelled(true);
    }
}
