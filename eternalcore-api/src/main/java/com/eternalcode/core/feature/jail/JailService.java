package com.eternalcode.core.feature.jail;

import org.bukkit.Location;
import org.bukkit.entity.Player;



public interface JailService {

    /**
     * Changes location of the jail.
     *
     * @param jailLocation The location of the jail.
     * @param setter Player who sets the jail location.
     */
    void setupJailArea(Location jailLocation, Player setter);

    /**
     * Removes the jail location.
     *
     * @param player The player who removes the jail location.
     */
    void removeJailArea(Player remover);

    /**
     * Returns true if the jail location is set.
     */
    boolean isLocationSet();

    /**
     * Provides the jail location.
     */
    Location getJailPosition();

}
