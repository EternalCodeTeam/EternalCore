package com.eternalcode.core.feature.afk;

import java.util.UUID;

/**
 * This service interface handles AFK (Away From Keyboard) status for players.
 */
public interface AfkService {

    /**
     * Checks if a uniqueId is marked as AFK.
     *
     * @param uniqueId Unique identifier of the uniqueId.
     * @return true if uniqueId is AFK, otherwise false.
     */
    boolean isAfk(UUID uniqueId);

    /**
     * Switches player's AFK status.
     *
     * @param uniqueId Unique identifier of the player.
     * @param reason Reason for switching AFK status.
     */
    void switchAfk(UUID uniqueId, AfkReason reason);

    /**
     * Marks interaction of a uniqueId. Typically, it is a mark of activity.
     * Default configuration of the plugin requires 20 interactions to mark the player as active.
     *
     * @param uniqueId Unique identifier of the player.
     */
    void markInteraction(UUID uniqueId);

    /**
     * Clears the AFK status of a uniqueId.
     *
     * @param uniqueId Unique identifier of the player.
     */
    void clearAfk(UUID uniqueId);

    /**
     * Marks player as AFK and returns the AFK status.
     *
     * @param player Unique identifier of the player.
     * @param reason Reason for being AFK.
     * @return Created an Afk object representing player's AFK status.
     */
    Afk markAfk(UUID player, AfkReason reason);
}