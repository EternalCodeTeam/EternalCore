package com.eternalcode.core.feature.jail;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

@Controller
class JailController implements Listener {

    private static final Set<TeleportCause> CANCELLED_CAUSES = Set.of(
        TeleportCause.CHORUS_FRUIT,
        TeleportCause.COMMAND,
        TeleportCause.ENDER_PEARL
    );

    private final JailService jailService;
    private final JailSettings jailSettings;
    private final NoticeService noticeService;

    @Inject
    public JailController(JailService jailService, JailSettings jailSettings, NoticeService noticeService) {
        this.jailService = jailService;
        this.noticeService = noticeService;
        this.jailSettings = jailSettings;
    }

    @EventHandler
    public void onPlayerPreCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (!this.jailService.isPlayerJailed(player.getUniqueId())) {
            return;
        }

        if (player.hasPermission("eternalcore.jail.bypass")) {
            return;
        }

        String command = event.getMessage().split(" ")[0].substring(1);
        Set<String> restrictedCommands = this.jailSettings.restrictedCommands();
        JailCommandRestrictionType restrictionType = this.jailSettings.restrictionType();

        boolean shouldBlockCommand = switch (restrictionType) {
            case WHITELIST -> !restrictedCommands.contains(command);
            case BLACKLIST -> restrictedCommands.contains(command);
        };

        if (!shouldBlockCommand) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailCannotUseCommand())
            .player(player.getUniqueId())
            .send();

        event.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent teleportEvent) {
        UUID uniqueId = teleportEvent.getPlayer().getUniqueId();

        if (!this.jailService.isPlayerJailed(uniqueId)) {
            return;
        }

        if (CANCELLED_CAUSES.contains(teleportEvent.getCause())) {
            teleportEvent.setCancelled(true);
        }
    }

}
