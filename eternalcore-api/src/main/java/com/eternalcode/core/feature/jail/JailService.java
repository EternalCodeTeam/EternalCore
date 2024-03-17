package com.eternalcode.core.feature.jail;

import org.bukkit.Location;

import java.util.Optional;

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
     * Provides the jail location.
     */
    Optional<Location> getJailLocation();

}
