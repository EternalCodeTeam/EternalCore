package com.eternalcode.core.feature.punishment.mute;

import static com.eternalcode.core.feature.punishment.PunishmentPermissionConstant.MUTE_BYPASS;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.PlayerOpenSignEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
@PermissionDocs(
    name = "Mute Bypass",
    permission = MUTE_BYPASS,
    description = "Permission allows to bypass being muted"
)
class MuteController implements Listener {

    private final PunishmentSettings punishmentSettings;
    private final MuteService muteService;
    private final NoticeService noticeService;

    @Inject
    MuteController(PunishmentSettings punishmentSettings, MuteService muteService, NoticeService noticeService) {
        this.punishmentSettings = punishmentSettings;
        this.muteService = muteService;
        this.noticeService = noticeService;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onSign(PlayerOpenSignEvent event) {
        if (!this.punishmentSettings.blockUsingSignOnMute()) {
            return;
        }

        Mute activeMute = this.resolveBlockingMute(event, event.getPlayer());

        if (activeMute != null) {
            String remainingText = this.remainingText(activeMute);

            this.noticeService.create()
                .notice(translation -> translation.punishment().muteBlockedSign())
                .placeholder("{REASON}", activeMute.reason())
                .placeholder("{REMAINING_TIME}", remainingText)
                .player(event.getPlayer().getUniqueId())
                .send();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onChat(AsyncChatEvent event) {
        Mute activeMute = this.resolveBlockingMute(event, event.getPlayer());

        if (activeMute != null) {
            String remainingText = this.remainingText(activeMute);

            this.noticeService.create()
                .notice(translation -> translation.punishment().muteBlockedChat())
                .placeholder("{REASON}", activeMute.reason())
                .placeholder("{REMAINING_TIME}", remainingText)
                .player(event.getPlayer().getUniqueId())
                .send();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onCommand(PlayerCommandPreprocessEvent event) {
        if (!this.punishmentSettings.blockCommandsOnMute()) {
            return;
        }

        String commandLabel = this.extractCommandLabel(event.getMessage());

        if (!this.punishmentSettings.blockedMuteCommands().contains(commandLabel)) {
            return;
        }

        Mute activeMute = this.resolveBlockingMute(event, event.getPlayer());

        if (activeMute != null) {
            String remainingText = this.remainingText(activeMute);

            this.noticeService.create()
                .notice(translation -> translation.punishment().muteBlockedCommand())
                .placeholder("{REASON}", activeMute.reason())
                .placeholder("{REMAINING_TIME}", remainingText)
                .player(event.getPlayer().getUniqueId())
                .send();
        }
    }

    private String extractCommandLabel(String message) {
        String withoutSlash = message.startsWith("/") ? message.substring(1) : message;
        String firstToken = withoutSlash.split(" ", 2)[0];
        String label = firstToken.contains(":") ? firstToken.substring(firstToken.indexOf(':') + 1) : firstToken;

        return label.toLowerCase(Locale.ROOT);
    }

    private Mute resolveBlockingMute(Cancellable event, Player player) {
        if (player.hasPermission(MUTE_BYPASS)) {
            return null;
        }

        UUID uniqueId = player.getUniqueId();
        Mute activeMute = this.muteService.getActiveMute(uniqueId);

        if (activeMute != null) {
            event.setCancelled(true);
        }

        return activeMute;
    }

    private String remainingText(Mute punishment) {
        return punishment.isPermanent()
            ? this.punishmentSettings.permanentLabel()
            : DurationUtil.format(Duration.between(Instant.now(), punishment.expiresAtOptional().orElseThrow()), true);
    }
}
