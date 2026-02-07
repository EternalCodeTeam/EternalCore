package com.eternalcode.core.feature.randomteleport;

import org.bukkit.Location;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record RandomTeleportResult(boolean success, Location location) {
}
