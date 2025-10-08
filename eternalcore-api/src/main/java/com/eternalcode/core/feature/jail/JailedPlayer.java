package com.eternalcode.core.feature.jail;

import org.bukkit.Location;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public interface JailedPlayer {

    /**
     * Gets the player's UUID.
     * @return player's UUID.
     */
    UUID playerUniqueId();

    /**
     * Gets the time when the player was detained.
     * @return time when the player was detained.
     */
    Instant detainedAt();

    /**
     * Gets the name of the player who detained the player.
     * @return name of player.
     */
    String detainedBy();

    /**
     * Gets the time the player is jailed.
     * @return time the player is jailed.
     */
    Duration prisonTime();

    /**
     * Gets the jail position where the player is teleported when detained.
     * @return last location of player.
     */
    Location lastLocation();

    /**
     * Checks if the jail has expired.
     * @return true if the jail has expired, false otherwise.
     */
    boolean isPrisonExpired();

    /**
     * Gets the time when the player was released.
     * @return time when the player was released.
     */
    Instant releaseTime();

    /**
     * Gets the remaining time of the jail.
     * @return remaining time of the jail.
     */
    Duration remainingTime();
}
