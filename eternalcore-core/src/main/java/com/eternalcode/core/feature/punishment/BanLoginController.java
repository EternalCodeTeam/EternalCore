package com.eternalcode.core.feature.punishment;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.ip.IpPunishmentService;
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

import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@PermissionDocs(
    name = "Notify when banned",
    permission = PunishmentPermissions.STAFF_MESSAGES,
    description = "Sending a message to the staff when a banned player attempts to log in."
)
class BanLoginController implements Listener {

    private final Map<UUID, Instant> lastStaffNotification = new ConcurrentHashMap<>();

    private final PunishmentService punishmentService;
    private final IpPunishmentService ipPunishmentService;
    private final PunishmentSettings punishmentSettings;
    private final TemplateMessageRenderer templateRenderer;
    private final NoticeService noticeService;
    private final Server server;

    @Inject
    BanLoginController(
        PunishmentService punishmentService,
        IpPunishmentService ipPunishmentService,
        PunishmentSettings punishmentSettings,
        TemplateMessageRenderer templateRenderer,
        NoticeService noticeService,
        Server server
    ) {
        this.punishmentService = Objects.requireNonNull(punishmentService, "punishmentService cannot be null");
        this.ipPunishmentService = Objects.requireNonNull(ipPunishmentService, "ipPunishmentService cannot be null");
        this.punishmentSettings = Objects.requireNonNull(punishmentSettings, "punishmentSettings cannot be null");
        this.templateRenderer = Objects.requireNonNull(templateRenderer, "templateRenderer cannot be null");
        this.noticeService = Objects.requireNonNull(noticeService, "noticeService cannot be null");
        this.server = Objects.requireNonNull(server, "server cannot be null");
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
            : DurationUtil.format(Duration.between(Instant.now(), punishment.expiresAt().orElseThrow()), true);

        List<Component> kickMessage = this.templateRenderer.render(
            this.punishmentSettings.banKickScreen(),
            Map.of(
                "{PLAYER}", event.getPlayer().getName(),
                "{OPERATOR}", punishment.operator().name(),
                "{REASON}", punishment.reason(),
                "{EXPIRES}", expiresText
            )
        );

        this.banEvasionIpIfNeeded(event.getAddress(), punishment, kickMessage);

        Component joined = Component.join(JoinConfiguration.newlines(), kickMessage);

        event.disallow(PlayerLoginEvent.Result.KICK_BANNED, joined);

        if (this.punishmentSettings.messageWhenBanned() && this.shouldNotifyStaff(targetUuid)) {
            this.notifyStaff(event.getPlayer().getName());
        }
    }

    private void banEvasionIpIfNeeded(InetAddress address, Punishment punishment, List<Component> kickMessage) {
        if (address == null) {
            return;
        }

        String ip = address.getHostAddress();

        if (this.ipPunishmentService.isIpBanned(ip)) {
            return;
        }

        this.ipPunishmentService.banIp(
            ip,
            punishment.target(),
            punishment.operator(),
            punishment.reason(),
            punishment.expiresAt().orElse(null),
            kickMessage
        );
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
