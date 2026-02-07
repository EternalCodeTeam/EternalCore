package com.eternalcode.core.feature.vanish;

import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

public interface VanishService {

    /**
     * Enables vanish for the specified player.
     *
     * @param player The player to enable vanish for.
     */
    void enableVanish(Player player);

    /**
     * Disables vanish for the specified player.
     *
     * @param player The player to disable vanish for.
     */
    void disableVanish(Player player);

    /**
     * Checks if the specified player is vanished.
     *
     * @param player The player to check.
     * @return true if the player is vanished, false otherwise.
     */
    boolean isVanished(Player player);

    default boolean toggleVanish(Player player) {
        if (isVanished(player)) {
            disableVanish(player);
            return false;
        }
        enableVanish(player);
        return true;
    }

    /**
     * Checks if the player with the specified unique ID is vanished.
     *
     * @param uniqueId The unique ID of the player to check.
     * @return true if the player is vanished, false otherwise.
     */
    boolean isVanished(UUID uniqueId);

    /**
     * Hides vanished players from the specified player.
     *
     * @param observer The player from whom vanished players should be hidden.
     */
    @ApiStatus.Internal
    void hideVanishedPlayersFrom(Player observer);

    /**
     * Gets a set of UUIDs of all vanished players.
     *
     * @return A set containing the unique IDs of all vanished players.
     */
    Set<UUID> getVanishedPlayers();

    /**
     * Gets a set of names of all vanished players.
     *
     * @return A set containing the names of all vanished players.
     */
    Set<String> getVanishedPlayerNames();

}
