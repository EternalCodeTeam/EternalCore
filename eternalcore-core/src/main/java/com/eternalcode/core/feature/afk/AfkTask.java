package com.eternalcode.core.feature.afk;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import java.util.concurrent.TimeUnit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;

@Task(delay = 1L, period = 1L, unit = TimeUnit.MINUTES)
class AfkTask implements Runnable {

    private final AfkService afkService;
    private final AfkServiceImpl afkServiceImpl;
    private final AfkSettings afkSettings;
    private final VanishService vanishService;
    private final Server server;

    @Inject
    public AfkTask(
        AfkService afkService,
        AfkServiceImpl afkServiceImpl,
        AfkSettings afkSettings,
        VanishService vanishService,
        Server server
    ) {
        this.afkService = afkService;
        this.afkServiceImpl = afkServiceImpl;
        this.afkSettings = afkSettings;
        this.vanishService = vanishService;
        this.server = server;
    }

    @Override
    public void run() {
        this.markAllInactivePlayers();
    }

    void markAllInactivePlayers() {
        if (!this.afkSettings.markPlayerAsAfk()) {
            return;
        }

        for (Player player : this.server.getOnlinePlayers()) {
            UUID playerUuid = player.getUniqueId();

            if (this.afkService.isAfk(playerUuid) || this.vanishService.isVanished(playerUuid)) {
                continue;
            }

            if (this.afkServiceImpl.isInactive(playerUuid)) {
                this.afkService.markAfk(playerUuid, AfkReason.INACTIVITY);
            }
        }
    }

}
