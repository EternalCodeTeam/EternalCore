package com.eternalcode.core.feature.warp;

import org.bukkit.Location;

import java.util.Collection;
import java.util.Optional;

public interface WarpService {

    Warp create(String name, Location location);

    void delete(String warp);

    void addPermissions(String warp, String... permissions);

    void removePermission(String warp, String permission);

    boolean exists(String name);

    boolean hasPermission(String warp, String permission);

    Optional<Warp> findWarp(String name);

    Collection<String> getAllNames();

    boolean isEmpty();
}
