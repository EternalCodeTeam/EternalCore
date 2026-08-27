package com.eternalcode.core.feature.notarget;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface MobTargetService {

    /*
     * Removes mob tracking for player.
     */
    void removeTracking(Player player);

    /*
     * Checks if a player is ignored for mob targeting.
     * @return true if the player is ignored, false otherwise.
     */
    boolean doMobsIgnore(UUID uniqueId);

    /*
     * Removes a player from the mob targeting ignore list.
     */
    void startTracking(UUID uniqueId);
}
