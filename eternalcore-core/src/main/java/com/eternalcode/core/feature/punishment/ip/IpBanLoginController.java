package com.eternalcode.core.feature.punishment.ip;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.util.DurationUtil;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
class IpBanLoginController implements Listener {

    private final IpPunishmentService ipPunishmentService;
    private final PunishmentSettings punishmentSettings;
    private final TemplateMessageRenderer templateRenderer;

    @Inject
    IpBanLoginController(
        IpPunishmentService ipPunishmentService,
        PunishmentSettings punishmentSettings,
        TemplateMessageRenderer templateRenderer
    ) {
        this.ipPunishmentService = ipPunishmentService;
        this.punishmentSettings = punishmentSettings;
        this.templateRenderer = templateRenderer;
    }

    @EventHandler
    void onLogin(PlayerLoginEvent event) {
        InetAddress realAddress = event.getRealAddress();

        if (realAddress == null) {
            return;
        }

        String ip = realAddress.getHostAddress();
        Optional<IpPunishment> activeBan = this.ipPunishmentService.getActiveIpBan(ip);

        if (activeBan.isEmpty()) {
            return;
        }

        IpPunishment punishment = activeBan.get();

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

        Component joined = Component.join(JoinConfiguration.newlines(), kickMessage);

        event.disallow(PlayerLoginEvent.Result.KICK_BANNED, joined);
    }
}
