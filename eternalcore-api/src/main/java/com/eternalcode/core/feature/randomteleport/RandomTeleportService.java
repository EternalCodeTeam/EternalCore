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
     * Asynchronously teleports the specified player to a random location within the specified world.
     *
     * @param player The player to teleport.
     * @param world  The world to which the player should be teleported.
     * @return A CompletableFuture containing the TeleportResult indicating the success or failure of the teleportation.
     */
    CompletableFuture<TeleportResult> teleport(Player player, World world);

    /**
     * Asynchronously retrieves a safe random location within the specified world.
     * Using default configuration values for a teleport type and radius.
     *
     * @param world        The world in which to find a random location.
     * @param attemptCount The number of attempts to find a safe location.
     * @return A CompletableFuture containing the random Location that is deemed safe.
     */
    CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount);

    /**
     * Asynchronously retrieves a safe random location within the specified world, using radius.
     *
     * @param world        The world in which to find a random location.
     * @param radius       The radius around the player to search for a safe location.
     * @param attemptCount The number of attempts to find a safe location.
     * @return A CompletableFuture containing the random Location that is deemed safe.
     */
    CompletableFuture<Location> getSafeRandomLocation(World world, int radius, int attemptCount);

    /**
     * Asynchronously retrieves a safe random location within the border in specified world.
     *
     * @param world        The world in which to find a random location.
     * @param attemptCount The number of attempts to find a safe location.
     * @return A CompletableFuture containing the random Location that is deemed safe.
     */
    CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount);


}
