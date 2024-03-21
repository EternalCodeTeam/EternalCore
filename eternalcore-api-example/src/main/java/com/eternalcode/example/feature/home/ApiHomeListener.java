package com.eternalcode.example.feature.home;

import com.eternalcode.core.feature.home.event.HomeLimitReachedEvent;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ApiHomeListener implements Listener {

    private final Server server;

    public ApiHomeListener(Server server) {
        this.server = server;
    }

    @EventHandler
    void homeLimit(HomeLimitReachedEvent event) {
        UUID playerUniqueId = event.getPlayerUniqueId();
        Player player = this.server.getPlayer(playerUniqueId);

        String title = "Home Limit Reached";
        String subtitle = String.format(
            "You have reached the maximum amount of homes (%d/%d).",
            event.getLimitAmount(),
            event.getMaxLimit()
        );

        player.sendTitle(title, subtitle, 10, 70, 20);
    }
}
