package com.eternalcode.core.feature.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;

/**
 * Service for teleporting players.
 */
@NullMarked
public interface TeleportService {

    /**
     * Teleport a player to a location.
     */
    void teleport(Player player, Location location);

    /**
     * Get the last location of a player before they teleported or died.
     */
    Optional<Location> getLastLocation(UUID player);

    /**
     * Mark the last location of a player.
     */
    @ApiStatus.Internal
    void markLastLocation(UUID player, Location location);

}
