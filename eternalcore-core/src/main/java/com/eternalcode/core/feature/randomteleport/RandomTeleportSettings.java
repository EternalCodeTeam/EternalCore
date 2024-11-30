package com.eternalcode.core.feature.randomteleport;

import java.time.Duration;
import java.util.Set;
import org.bukkit.Material;

interface RandomTeleportSettings {

    Duration randomTeleportTime();

    RandomTeleportRadius randomTeleportRadius();

    RandomTeleportType randomTeleportType();

    String randomTeleportWorld();

    int randomTeleportAttempts();

    Set<Material> randomTeleportUnsafeBlocks();

    Set<Material> randomTeleportAirBlocks();

    RandomTeleportHeightRange heightRange();


}
