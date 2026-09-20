package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
class BanLoginController implements Listener {

    private final Map<UUID, Instant> lastStaffNotification = new ConcurrentHashMap<>();

    private final PunishmentService punishmentService;
    private final PunishmentSettings punishmentSettings;
    private final TemplateMessageRenderer templateRenderer;
    private final NoticeService noticeService;
    private final Server server;

    @Inject
    BanLoginController(
        PunishmentService punishmentService,
        PunishmentSettings punishmentSettings,
        TemplateMessageRenderer templateRenderer,
        NoticeService noticeService,
        Server server
    ) {
        this.punishmentService = punishmentService;
        this.punishmentSettings = punishmentSettings;
        this.templateRenderer = templateRenderer;
        this.noticeService = noticeService;
        this.server = server;
    }

    @EventHandler
    void onLogin(PlayerLoginEvent event) {
        UUID targetUuid = event.getPlayer().getUniqueId();

        Optional<Punishment> activeBan = this.punishmentService.getActiveBan(targetUuid);

        if (activeBan.isEmpty()) {
            return;
        }

        Punishment punishment = activeBan.get();

        String expiresText = punishment.isPermanent()
            ? this.punishmentSettings.permanentLabel()
            : DurationUtil.format(Duration.between(Instant.now(), punishment.expiresAtOptional().orElseThrow()), true);

        List<Component> kickMessage = this.templateRenderer.render(
            this.punishmentSettings.banKickScreen(),
            Map.of(
                "{PLAYER}", event.getPlayer().getName(),
                "{OPERATOR}", punishment.operator().name(),
                "{REASON}", punishment.reason(),
                "{EXPIRES}", expiresText
            )
        );

        Component joined = Component.join(JoinConfiguration.newlines(), kickMessage);

        event.disallow(PlayerLoginEvent.Result.KICK_BANNED, joined);

        if (this.punishmentSettings.messageWhenBanned() && this.shouldNotifyStaff(targetUuid)) {
            this.notifyStaff(event.getPlayer().getName());
        }
    }

    private boolean shouldNotifyStaff(UUID targetUuid) {
        Instant now = Instant.now();
        Instant last = this.lastStaffNotification.get(targetUuid);

        if (last != null && Duration.between(last, now).compareTo(this.punishmentSettings.messageWhenBannedCooldown()) < 0) {
            return false;
        }

        this.lastStaffNotification.put(targetUuid, now);
        return true;
    }

    private void notifyStaff(String playerName) {
        var notice = this.noticeService.create()
            .notice(translation -> translation.punishment().banPlayerTriesJoin())
            .placeholder("{PLAYER}", playerName);

        for (Player staff : this.server.getOnlinePlayers()) {
            if (staff.hasPermission(PunishmentPermissions.STAFF_MESSAGES)) {
                notice = notice.player(staff.getUniqueId());
            }
        }

        notice.send();
    }
}
