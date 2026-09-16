package com.eternalcode.core.feature.mobignore;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface MobIgnoreService {

    /*
     * Removes mob tracking for player.
     */
    void stopTracking(Player player);

    /*
     * Checks if a player is ignored for mob targeting.
     * @return true if the player cannot be tracked by mobs.
     */
    boolean isIgnored(UUID uniqueId);

    /*
     * Player will now be targeted by mobs.
     */
    void startTracking(UUID uniqueId);
}
