package com.eternalcode.core.feature.spawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

public interface SpawnService {

    /**
     * Teleports the player to the spawn location.
     *
     * @param player The player to teleport to the spawn location.
     */
    void teleportToSpawn(Player player);


    /**
     * Set the spawn location in the game.
     */
    void setSpawnLocation(Location location);

    /**
     * Provides the spawn location in the game.
     */
    @Nullable Location getSpawnLocation();
}