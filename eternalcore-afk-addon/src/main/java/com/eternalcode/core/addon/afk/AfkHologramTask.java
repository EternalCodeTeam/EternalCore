package com.eternalcode.core.addon.afk;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AfkHologramTask implements Runnable {

    private final AfkHologramService afkHologramService;
    private final Server server;

    public AfkHologramTask(AfkHologramService afkHologramService, Server server) {
        this.afkHologramService = afkHologramService;
        this.server = server;
    }

    @Override
    public void run() {
        for (UUID uuid : this.afkHologramService.getAfkPlayers()) {
            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                this.afkHologramService.deleteHologram(uuid);
                continue;
            }

            this.afkHologramService.updateHologram(player);
        }
    }
}
