package com.eternalcode.core.feature.randomteleport;

import org.bukkit.Location;

public record TeleportResult(boolean success, Location location) {
}
