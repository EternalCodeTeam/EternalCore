package com.eternalcode.core.feature.jail;

import org.bukkit.Location;

public interface JailService {

    /**
     * Changes location of the jail.
     *
     * @param jailLocation The location of the jail.
     */
    void setupJailArea(Location jailLocation);

    /**
     * Removes the jail location.
     */
    void removeJailArea();

    /**
     * Returns true if the jail location is set.
     */
    boolean isJailLocationSet();

    /**
     * Provides the jail location.
     */
    Location getJailLocation();

}
