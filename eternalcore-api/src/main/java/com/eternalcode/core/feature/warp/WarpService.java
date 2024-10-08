package com.eternalcode.core.feature.warp;

import org.bukkit.Location;

import java.util.Collection;
import java.util.Optional;

public interface WarpService {

    Warp createWarp(String name, Location location);

    void removeWarp(String warp);

    void addPermissions(String warp, String... permissions);

    void removePermission(String warp, String permission);

    boolean warpExists(String name);

    boolean doestWarpPermissionExist(String warp, String permission);

    Optional<Warp> findWarp(String name);

    Collection<String> getNamesOfWarps();

    boolean hasWarps();
}
