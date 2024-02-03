package com.eternalcode.core.feature.catboy;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Task(delay = 400L, period = 400L, unit = TimeUnit.MICROSECONDS)
class CatboyTask implements Runnable {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final CatboyService catboyService;
    private final Server server;

    @Inject
    CatboyTask(CatboyService catboyService, Server server) {
        this.catboyService = catboyService;
        this.server = server;
    }

    @Override
    public void run() {
        for (UUID catboy : this.catboyService.getCatboys()) {
            Player player = this.server.getPlayer(catboy);

            if (player == null) {
                continue;
            }

            for (int i = 0; i < 3; i++) {
                player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(
                    RANDOM.nextDouble(-1.5D, 1.5D),
                    RANDOM.nextDouble(0D, 2.0D),
                    RANDOM.nextDouble(-1.5D, 1.5D)
                ), 3);
            }
        }
    }

}
