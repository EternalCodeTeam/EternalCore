package com.eternalcode.core.feature.mobignore;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface MobIgnoreService {

    /**
     * Makes mobs ignore the given player.
     *
     * @param player the player to ignore
     */
    default void ignore(Player player) {
        this.ignore(player.getUniqueId());
    }
    /**
     * Allows mobs to target the given player again.
     *
     * @param player the player to stop ignoring
     */
    default void unignore(Player player) {
        this.unignore(player.getUniqueId());
    }
    /**
     * Checks whether mobs should ignore the given player.
     *
     * @param player the player to check
     * @return {@code true} if mobs should ignore the player, otherwise {@code false}
     */
    default boolean isIgnored(Player player) {
        return this.isIgnored(player.getUniqueId());
    }
    /**
     * Makes mobs ignore the player with the given unique ID.
     *
     * @param uniqueId the unique ID of the player
     */
    void ignore(UUID uniqueId);
    /**
     * Allows mobs to target the player with the given unique ID again.
     *
     * @param uniqueId the unique ID of the player
     */
    void unignore(UUID uniqueId);
    /**
     * Checks whether mobs should ignore the player with the given unique ID.
     *
     * @param uniqueId the unique ID of the player
     * @return {@code true} if mobs should ignore the player, otherwise {@code false}
     */
    boolean isIgnored(UUID uniqueId);
}
