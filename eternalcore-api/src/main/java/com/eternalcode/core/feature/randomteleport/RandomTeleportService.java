package com.eternalcode.core.feature.randomteleport;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface RandomTeleportService {

    /**
     * Asynchronously teleports the specified player to a random location.
     *
     * @param player The player to teleport.
     * @return A CompletableFuture containing the TeleportResult indicating the success or failure of the teleportation.
     */
    CompletableFuture<TeleportResult> teleport(Player player);

    /**
     * Asynchronously retrieves a safe random location within the specified world.
     * Using default configuration values for teleport type and radius.
     *
     * @param world        The world in which to find a random location.
     * @param attemptCount The number of attempts to find a safe location.
     * @return A CompletableFuture containing the random Location that is deemed safe.
     */
    CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount);

    /**
     * Asynchronously retrieves a safe random location within the specified world,
     * considering the specified RandomTeleportType, radius, and attempt count.
     *
     * @param world        The world in which to find a random location.
     * @param type         The type of random teleportation.
     * @param radius       The radius around the center for random teleportation.
     * @param attemptCount The number of attempts to find a safe location.
     * @return A CompletableFuture containing the random Location that is deemed safe.
     */
    CompletableFuture<Location> getSafeRandomLocation(World world, RandomTeleportType type, int radius, int attemptCount);

    /**
     * Checks whether the specified location within the given chunk is safe for teleportation.
     *
     * @param chunk    The chunk in which the location resides.
     * @param location The location to check for safety.
     * @return true if the location is safe; false otherwise.
     */
    boolean isSafeLocation(Chunk chunk, Location location);
}
