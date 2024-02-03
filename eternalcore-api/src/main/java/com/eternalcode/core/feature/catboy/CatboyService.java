package com.eternalcode.core.feature.catboy;

import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

/**
 * Service for managing catboys.
 */
public interface CatboyService {

    /**
     * Marks a player as a catboy.
     */
    void markAsCatboy(Player player);

    /**
     * Unmarks a player as a catboy.
     */
    void unmarkAsCatboy(Player player);

    /**
     * Checks if a player is a catboy.
     *
     * @param uuid The UUID of the player to check.
     * @return True, if the player is a catboy, otherwise false.
     */
    boolean isCatboy(UUID uuid);

    /**
     * Gets a set of all catboys.
     */
    Set<UUID> getCatboys();

}
