package com.eternalcode.core.feature.warp;

import org.bukkit.Location;

import java.util.Collection;
import java.util.Optional;

public interface WarpService {

    void createWarp(String name, Location location);

    void removeWarp(String warp);

    boolean warpExists(String name);

    Optional<Warp> findWarp(String name);

    Collection<String> getNamesOfWarps();
}
