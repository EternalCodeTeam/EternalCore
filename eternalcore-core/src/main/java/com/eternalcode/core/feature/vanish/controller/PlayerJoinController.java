package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishPermissionConstant;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
public class PlayerJoinController implements Listener {

    private final VanishService vanishService;
    private final Server server;

    @Inject
    public PlayerJoinController(VanishService vanishService, Server server) {
        this.vanishService = vanishService;
        this.server = server;
    }


    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission(VanishPermissionConstant.VANISH_JOIN_PERMISSION)) {
            event.setJoinMessage(null);
            this.vanishService.enableVanish(player);

            player.sendMessage("You have been automatically vanished upon joining the server.");
            this.server.broadcast(player.getName() + " has joined the server in vanish mode.", VanishPermissionConstant.VANISH_JOIN_PERMISSION);

            return;
        }

        if (player.hasPermission(VanishPermissionConstant.VANISH_SEE_PERMISSION)) {
            return;
        }

        this.vanishService.hideVanishedPlayersFrom(player);
    }
}
