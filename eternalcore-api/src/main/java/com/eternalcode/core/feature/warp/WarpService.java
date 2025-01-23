package com.eternalcode.core.feature.warp;

import org.bukkit.Location;

import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.Experimental;

public interface WarpService {

    Warp createWarp(String name, Location location);

    void removeWarp(String warp);

    @Experimental
    Warp addPermissions(String warp, String... permissions);

    @Experimental
    Warp removePermissions(String warp, String... permissions);

    boolean isExist(String name);

    Optional<Warp> findWarp(String name);

    Collection<Warp> getWarps();
}
