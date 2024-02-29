package com.eternalcode.core.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class JailController implements Listener {

    private final JailService jailService;
    private Map<UUID, Prisoner> jailedPlayers;
    private final NoticeService noticeService;

    private static final  Set<TeleportCause> CANCELLED_CAUSES = Set.of(
        TeleportCause.CHORUS_FRUIT,
        TeleportCause.COMMAND,
        TeleportCause.ENDER_PEARL
    );


    @Inject
    public JailController(JailService jailService, NoticeService noticeService, JailSettings settings) {
        this.jailService = jailService;
        this.noticeService = noticeService;
        this.jailedPlayers = this.jailService.getJailedPlayers();
    }

    @EventHandler
    public void onPlayerPreCommand(PlayerCommandPreprocessEvent event) {

        UUID player = event.getPlayer().getUniqueId();

        if (!this.jailedPlayers.containsKey(player)) {
            return;
        }

        String command = event.getMessage().split(" ")[0].substring(1);

        if (this.jailService.isAllowedCommand(command)) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.jailSection().playerCannotUseCommand())
            .player(player)
            .send();

        event.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent tp) {

        UUID player = tp.getPlayer().getUniqueId();

        if (!this.jailedPlayers.containsKey(player)) {
            return;
        }

        if (CANCELLED_CAUSES.contains(tp.getCause())) {
            tp.setCancelled(true);
        }
    }

    @EventHandler
    public void onJailRelease(JailReleaseEvent event) {
        if (event.isCancelled()) {
            return;
        }

        this.updateJailedPlayers();
    }

    @EventHandler
    public void onJailDetain(JailDetainEvent event) {
        if (event.isCancelled()) {
            return;
        }

        this.updateJailedPlayers();
    }


    public void updateJailedPlayers() {
        this.jailedPlayers = this.jailService.getJailedPlayers();
    }


}
