package com.eternalcode.core.feature.punishment.banip;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.BAN_IP_BYPASS;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.util.DurationUtil;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
class BanIpLoginController implements Listener {

    private static final String PLAYER_PLACEHOLDER = "{PLAYER}";
    private static final String OPERATOR_PLACEHOLDER = "{OPERATOR}";
    private static final String REASON_PLACEHOLDER = "{REASON}";
    private static final String EXPIRES_PLACEHOLDER = "{EXPIRES}";
    private static final boolean REMOVE_MILLIS = true;

    private final BanIpService banIpService;
    private final PunishmentSettings punishmentSettings;
    private final TemplateMessageRenderer templateRenderer;

    @Inject
    BanIpLoginController(
        BanIpService banIpService,
        PunishmentSettings punishmentSettings,
        TemplateMessageRenderer templateRenderer
    ) {
        this.banIpService = banIpService;
        this.punishmentSettings = punishmentSettings;
        this.templateRenderer = templateRenderer;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    void onLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }

        Player player = event.getPlayer();

        if (player.hasPermission(BAN_IP_BYPASS)) {
            return;
        }

        InetAddress address = event.getAddress();

        if (address == null) {
            return;
        }

        Optional<BanIp> activeBan = this.banIpService.getActiveIpBan(address.getHostAddress());

        if (activeBan.isEmpty()) {
            return;
        }

        event.disallow(PlayerLoginEvent.Result.KICK_BANNED, this.renderKickScreen(player, activeBan.get()));
    }

    private Component renderKickScreen(Player player, BanIp punishment) {
        List<Component> lines = this.templateRenderer.render(
            this.punishmentSettings.banIpKickScreen(),
            Map.of(
                PLAYER_PLACEHOLDER, player.getName(),
                OPERATOR_PLACEHOLDER, punishment.operator().name(),
                REASON_PLACEHOLDER, punishment.reason(),
                EXPIRES_PLACEHOLDER, this.expiresText(punishment)
            )
        );

        return Component.join(JoinConfiguration.newlines(), lines);
    }

    private String expiresText(BanIp punishment) {
        return punishment.expiresAtOptional()
            .map(expiresAt -> DurationUtil.format(Duration.between(Instant.now(), expiresAt), REMOVE_MILLIS))
            .orElse(this.punishmentSettings.permanentLabel());
    }
}
