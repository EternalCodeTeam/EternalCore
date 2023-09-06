package com.eternalcode.core.feature.afk;

import com.eternalcode.core.injector.annotations.component.Task;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Task(period = 1L, delay = 1L, unit = TimeUnit.MINUTES)
public class AfkTask implements Runnable {

    private final AfkService afkService;
    private final Server server;

    public AfkTask(AfkService afkService, Server server) {
        this.afkService = afkService;
        this.server = server;
    }

    @Override
    public void run() {
        this.markAllInactivePlayers();
    }

    void markAllInactivePlayers() {
        for (Player player : this.server.getOnlinePlayers()) {
            UUID playerUuid = player.getUniqueId();

            if (this.afkService.isAfk(playerUuid)) {
                continue;
            }

            if (this.afkService.isInactive(playerUuid)) {
                this.afkService.markAfk(playerUuid, AfkReason.INACTIVITY);
            }
        }
    }

}
