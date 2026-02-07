package com.eternalcode.core.feature.warp;

import org.bukkit.Location;
import org.jetbrains.annotations.ApiStatus.Experimental;

import java.util.Collection;
import java.util.Optional;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface WarpService {

    Warp createWarp(String warp, Location location);

    void removeWarp(String warp);

    @Experimental
    Warp addPermissions(String warp, String... permissions);

    @Experimental
    Warp removePermissions(String warp, String... permissions);

    boolean exists(String warp);

    Optional<Warp> findWarp(String warp);

    Collection<Warp> getWarps();
}
