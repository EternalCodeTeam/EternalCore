package com.eternalcode.core.feature.warp;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public record Warp(@NotNull String name, @NotNull Location location) {
}
