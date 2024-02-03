package com.eternalcode.core.feature.catboy;

import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing catboys.
 */
public interface CatboyService {

    /**
     * Marks a player as a catboy.
     *
     * @param player The player to mark as a catboy.
     * @param type The type of the catboy.
     */
    void markAsCatboy(Player player, Cat.Type type);

    /**
     * Unmarks a player as a catboy.
     *
     * @param player The player to unmark as a catboy.
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
     * Gets the catboy of a player.
     */
    Optional<Catboy> getCatboy(UUID uuid);

    /**
     * Gets a set of all catboys.
     */
    @Unmodifiable
    Collection<Catboy> getCatboys();

}
