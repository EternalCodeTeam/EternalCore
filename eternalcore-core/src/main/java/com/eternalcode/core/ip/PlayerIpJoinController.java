package com.eternalcode.core.ip;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.net.InetAddress;

@Controller
class PlayerIpJoinController implements Listener {

    private final PlayerIpService playerIpService;

    @Inject
    PlayerIpJoinController(PlayerIpService playerIpService) {
        this.playerIpService = playerIpService;
    }

    @EventHandler
    void onPreLogin(AsyncPlayerPreLoginEvent event) {
        InetAddress realAddress = event.getAddress();

        if (realAddress == null) {
            return;
        }

        this.playerIpService.recordLogin(event.getUniqueId(), event.getName(), realAddress.getHostAddress());
    }
}
