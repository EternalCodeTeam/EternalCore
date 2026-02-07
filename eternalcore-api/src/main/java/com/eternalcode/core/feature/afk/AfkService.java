package com.eternalcode.core.feature.afk;

import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;

/**
 * This service interface handles AFK (Away From Keyboard) status for players.
 */
@NullMarked
public interface AfkService {

    /**
     * Checks if a uniqueId is marked as AFK.
     *
     * @param playerUniqueId Unique identifier of the player.
     * @return true if uniqueId is AFK, otherwise false.
     */
    boolean isAfk(UUID playerUniqueId);

    /**
     * Switches player's AFK status.
     *
     * @param playerUniqueId Unique identifier of the player.
     * @param reason Reason for switching AFK status.
     */
    void switchAfk(UUID playerUniqueId, AfkReason reason);

    /**
     * Marks interaction of a uniqueId. Typically, it is a mark of activity.
     * Default configuration of the plugin requires 20 interactions to mark the player as active.
     *
     * @param playerUniqueId Unique identifier of the player.
     */
    void markInteraction(UUID playerUniqueId);

    /**
     * Clears the AFK status of a uniqueId.
     *
     * @param playerUniqueId Unique identifier of the player.
     */
    void clearAfk(UUID playerUniqueId);

    /**
     * Marks player as AFK and returns the AFK status.
     *
     * @param playerUniqueId Unique identifier of the player.
     * @param reason Reason for being AFK.
     * @return Created an Afk object representing player's AFK status.
     */
    Afk markAfk(UUID playerUniqueId, AfkReason reason);

    /**
     * Gets the AFK status of a uniqueId.
     *
     * @param playerUniqueId Unique identifier of the player.
     * @return Afk object representing player's AFK status.
     */
    Optional<Afk> getAfk(UUID playerUniqueId);
}
