package com.eternalcode.core.feature.jail;

import org.bukkit.Location;

import java.util.Optional;
import org.jetbrains.annotations.Blocking;

public interface JailService {

    /**
     * Changes location of the jail.
     *
     * @param jailLocation The location of the jail.
     */
    @Blocking
    void setupJailArea(Location jailLocation);

    /**
     * Removes the jail location.
     */
    @Blocking
    void removeJailArea();

    /**
     * Provides the jail location.
     */
    Optional<Location> getJailLocation();

}
