package com.eternalcode.core.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.Set;
import java.util.UUID;

@Controller
public class JailController implements Listener {

    private final PrisonerService prisonerService;
    private final NoticeService noticeService;

    private static final Set<TeleportCause> CANCELLED_CAUSES = Set.of(
        TeleportCause.CHORUS_FRUIT,
        TeleportCause.COMMAND,
        TeleportCause.ENDER_PEARL
    );


    @Inject
    public JailController(PrisonerService prisonerService, NoticeService noticeService) {
        this.prisonerService = prisonerService;
        this.noticeService = noticeService;
    }

    @EventHandler
    public void onPlayerPreCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (!this.prisonerService.getJailedPlayers().containsKey(player.getUniqueId())) {
            return;
        }

        if (player.hasPermission("eternalcore.jail.bypass")) {
            return;
        }

        String command = event.getMessage().split(" ")[0].substring(1);

        if (this.prisonerService.isAllowedCommand(command)) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.jailSection().playerCannotUseCommand())
            .player(player.getUniqueId())
            .send();

        event.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent teleportEvent) {

        UUID player = teleportEvent.getPlayer().getUniqueId();

        if (!this.prisonerService.getJailedPlayers().containsKey(player)) {
            return;
        }

        if (CANCELLED_CAUSES.contains(teleportEvent.getCause())) {
            teleportEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onJailRelease(JailReleaseEvent event) {
        if (event.isCancelled()) {
            return;
        }
    }

    @EventHandler
    public void onJailDetain(JailDetainEvent event) {

        if (event.getPlayer().hasPermission("eternalcore.jail.bypass")) {
            event.setCancelled(true);
        }

        if (event.isCancelled()) {
            return;
        }
    }


}
