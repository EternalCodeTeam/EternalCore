package com.eternalcode.core.feature.warp;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface WarpService {

    void createWarp(@NotNull String name,@NotNull Location location);

    void removeWarp(@NotNull String warp);

    boolean warpExists(@NotNull String name);

    Optional<Warp> findWarp(@NotNull String name);

    Collection<String> getNamesOfWarps();
}
