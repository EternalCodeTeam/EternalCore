package com.eternalcode.core.feature.randomteleport;

import java.time.Duration;
import java.util.Set;
import org.bukkit.Material;

interface RandomTeleportSettings {

    RandomTeleportRadius radius();

    RandomTeleportType radiusType();

    String world();

    int teleportAttempts();

    Set<Material> unsafeBlocks();

    Set<Material> airBlocks();

    RandomTeleportHeightRange heightRange();

    Duration delay();

    Duration cooldown();

}
