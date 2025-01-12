package com.eternalcode.example.feature.home;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.event.HomeCreateEvent;
import com.eternalcode.core.feature.home.event.HomeDeleteEvent;
import com.eternalcode.core.feature.home.event.HomeLimitReachedEvent;
import com.eternalcode.core.feature.home.event.HomeOverrideEvent;
import com.eternalcode.core.feature.home.event.HomeTeleportEvent;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public class ApiHomeListener implements Listener {

    public static final String HOME_OVERRIDE_FORMAT = "Home %s overridden to %s at %d, %d, %d.";
    public static final String HOME_DELETE_FORMAT = "Home %s deleted.";

    private final Server server;

    public ApiHomeListener(Server server) {
        this.server = server;
    }

    @EventHandler
    void onHomeOverride(HomeOverrideEvent event) {
        Location location = event.getLocation();

        String name = event.getHomeName();
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();
        System.out.printf((HOME_OVERRIDE_FORMAT) + "%n",
            name, location.getWorld().getName(), blockX, blockY, blockZ);
    }

    @EventHandler
    void onHomeCreate(HomeCreateEvent event) {
        Location location = event.getLocation();

        String name = event.getHomeName();
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();
        System.out.printf((HOME_OVERRIDE_FORMAT) + "%n",
            name, location.getWorld().getName(), blockX, blockY, blockZ);
    }

    @EventHandler
    void onHomeCreateTroll(HomeCreateEvent event) {
        UUID playerUniqueId = event.getPlayerUniqueId();
        Player player = this.server.getPlayer(playerUniqueId);

        if (player == null) {
            return;
        }

        if (player.hasPotionEffect(PotionEffectType.BAD_OMEN)) {
            event.setLocation(player.getWorld().getSpawnLocation());
            event.setHomeName("bimbimbambam");
            System.out.println("Troll: Home location overridden to world spawn.");
        }
    }

    @EventHandler
    void onHomeDelete(HomeDeleteEvent event) {
        Home home = event.getHome();

        String name = home.getName();
        System.out.printf((HOME_DELETE_FORMAT) + "%n", name);
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

    @EventHandler
    void onHomeTeleport(HomeTeleportEvent event) {
        UUID playerUniqueId = event.getPlayerUniqueId();

        Player player = this.server.getPlayer(playerUniqueId);

        if (player == null) {
            return;
        }

        player.sendMessage("Teleporting to home...");

        if (player.hasPotionEffect(PotionEffectType.SLOWNESS)) {
            player.sendMessage("You are slowed down!");
            player.teleport(player.getWorld().getSpawnLocation());
        }
    }
}
