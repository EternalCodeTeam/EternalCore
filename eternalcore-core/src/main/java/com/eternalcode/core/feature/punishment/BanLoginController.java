package com.eternalcode.core.feature.punishment;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.util.DurationUtil;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
class BanLoginController implements Listener {

    private final PunishmentService punishmentService;
    private final PunishmentSettings punishmentSettings;
    private final TemplateMessageRenderer templateRenderer;

    @Inject
    BanLoginController(
        PunishmentService punishmentService,
        PunishmentSettings punishmentSettings,
        TemplateMessageRenderer templateRenderer
    ) {
        this.punishmentService = punishmentService;
        this.punishmentSettings = punishmentSettings;
        this.templateRenderer = templateRenderer;
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

        Component joined = Component.join(JoinConfiguration.newlines(), kickMessage);

        event.disallow(PlayerLoginEvent.Result.KICK_BANNED, joined);
    }
}
