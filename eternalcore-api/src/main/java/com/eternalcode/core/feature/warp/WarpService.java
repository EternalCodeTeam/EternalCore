package com.eternalcode.core.feature.warp;

import org.bukkit.Location;
import org.jetbrains.annotations.ApiStatus.Experimental;

import java.util.Collection;
import java.util.Optional;

public interface WarpService {

    Warp createWarp(String name, Location location);

    void removeWarp(String warp);

    @Experimental
    Warp addPermissions(String warp, String... permissions);

    @Experimental
    Warp removePermissions(String warp, String... permissions);

    boolean exists(String name);

    Optional<Warp> findWarp(String name);

    Collection<Warp> getWarps();
}
