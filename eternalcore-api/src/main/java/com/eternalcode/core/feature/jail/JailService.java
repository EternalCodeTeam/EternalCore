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

    void removeJailArea(Player remover);

    boolean isLocationSet();

    Location getJailPosition();

}
