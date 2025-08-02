package com.eternalcode.core.feature.fun.catboy;

import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Task(delay = 400L, period = 400L, unit = TimeUnit.MILLISECONDS)
class CatboyTask implements Runnable {

    private static final String CATBOY_NAME = "Catboy";
    private static final int PARTICLES_COUNT = 4;

    private final CatboyService catboyService;
    private final Server server;
    private final MinecraftScheduler scheduler;

    @Inject
    CatboyTask(CatboyService catboyService, Server server, MinecraftScheduler scheduler) {
        this.catboyService = catboyService;
        this.server = server;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        for (Catboy catboy : this.catboyService.getCatboys()) {
            Player player = this.server.getPlayer(catboy.uuid());

            if (player == null) {
                continue;
            }

            this.animateCatboy(player);
        }
    }

    private void animateCatboy(Player player) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < PARTICLES_COUNT; i++) {
            player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(
                random.nextDouble(-1.5D, 1.5D),
                random.nextDouble(0D, 2.0D),
                random.nextDouble(-1.5D, 1.5D)
            ), 3);
        }

        for (Entity passenger : player.getPassengers()) {
            if (!(passenger instanceof Cat cat)) {
                continue;
            }

            DyeColor dyeColor = this.randomDyeColor();
            Color color = new Color(dyeColor.getColor().asRGB());

            scheduler.run(cat, () -> {
                cat.setCollarColor(dyeColor);
                cat.setCustomName(ChatColor.of(color) + CATBOY_NAME);
                cat.setRotation(player.getLocation().getYaw(), cat.getLocation().getPitch());
            });
            break;
        }
    }

    private DyeColor randomDyeColor() {
        return DyeColor.values()[ThreadLocalRandom.current().nextInt(DyeColor.values().length)];
    }

}
