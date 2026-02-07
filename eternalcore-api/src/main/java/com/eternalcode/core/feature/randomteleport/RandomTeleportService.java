package com.eternalcode.core.feature.randomteleport;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface RandomTeleportService {

    /**
     * Asynchronously teleports the specified player to a random location.
     *
     * @param player The player to teleport.
     * @return A CompletableFuture containing the TeleportResult indicating the success or failure of the teleportation.
     */
    CompletableFuture<RandomTeleportResult> teleport(Player player);

    /**
     * Asynchronously teleports the specified player to a random location within the specified world.
     *
     * @param player The player to teleport.
     * @param world  The world to which the player should be teleported.
     * @return A CompletableFuture containing the TeleportResult indicating the success or failure of the teleportation.
     */
    CompletableFuture<RandomTeleportResult> teleport(Player player, World world);

    /**
     * Asynchronously teleports a collection of players to the same random location.
     * <p>
     * All players will be teleported to the same safe location.
     * The world is determined from an arbitrary player in the collection
     * if not specified otherwise. If players are from different worlds,
     * the chosen world may vary depending on the iteration order of the collection.
     *
     * @param players The collection of players to teleport together.
     * @return A CompletableFuture containing a map of each player to their TeleportResult.
     */
    CompletableFuture<Map<Player, RandomTeleportResult>> teleport(Collection<Player> players);


    /**
     * Asynchronously teleports a collection of players to the same random location within the specified world.
     * All players will be teleported to the same safe location.
     *
     * @param players The collection of players to teleport together.
     * @param world   The world to which the players should be teleported.
     * @return A CompletableFuture containing a map of each player to their TeleportResult.
     */
    CompletableFuture<Map<Player, RandomTeleportResult>> teleport(Collection<Player> players, World world);

    /**
     * Asynchronously teleports a collection of players to the same specific location.
     *
     * @param players  The collection of players to teleport.
     * @param location The exact location to teleport all players to.
     * @return A CompletableFuture containing a map of each player to their TeleportResult.
     */
    CompletableFuture<Map<Player, RandomTeleportResult>> teleport(Collection<Player> players, Location location);

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
     * Asynchronously retrieves a safe random location within the specified world, using radius.
     *
     * @param world        The world in which to find a random location.
     * @param radius       The radius around the player to search for a safe location.
     * @param attemptCount The number of attempts to find a safe location.
     * @return A CompletableFuture containing the random Location that is deemed safe.
     */
    CompletableFuture<Location> getSafeRandomLocation(World world, RandomTeleportRadius radius, int attemptCount);

    /**
     * Asynchronously retrieves a safe random location within the border in specified world.
     *
     * @param world        The world in which to find a random location.
     * @param attemptCount The number of attempts to find a safe location.
     * @return A CompletableFuture containing the random Location that is deemed safe.
     */
    CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount);

}
