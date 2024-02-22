package com.eternalcode.core.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Map;
import java.util.UUID;

@Controller
public class JailController implements Listener {

    private final JailService jailService;
    private Map<UUID, Prisoner> jailedPlayers;
    private final NoticeService noticeService;


    @Inject
    public JailController(JailService jailService, NoticeService noticeService) {
        this.jailService = jailService;
        this.noticeService = noticeService;
        this.jailedPlayers = this.jailService.getJailedPlayers();
    }

    @EventHandler
    public void onPlayerCommandSend(PlayerCommandSendEvent event) {

        UUID player = event.getPlayer().getUniqueId();

        if (!this.jailedPlayers.containsKey(player)) {
            return;
        }
        event.getCommands().clear();
        this.noticeService.create()
            .notice(translation -> translation.jailSection().playerCannotUseCommand())
            .player(player)
            .send();

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent tp) {

        UUID player = tp.getPlayer().getUniqueId();

        if (!this.jailedPlayers.containsKey(player)) {
            return;
        }
        if (tp.getCause().equals(PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT)
            || tp.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)) {
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
