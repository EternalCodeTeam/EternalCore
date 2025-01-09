package com.eternalcode.core.feature.randomteleport;

import static org.bukkit.Bukkit.getWorld;

import org.bukkit.World;
import org.bukkit.entity.Player;

final class RandomTeleportResolveWorldUtil {

    static World resolveWorld(Player player, RandomTeleportSettings settings) {
        World world = player.getWorld();

        if (!settings.world().isBlank()) {
            world = getWorld(settings.world());

            if (world == null) {
                throw new IllegalStateException(
                    "World " + settings.world() + " does not exist!");
            }
        }

        return world;
    }
}
