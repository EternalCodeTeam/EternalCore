package com.eternalcode.core.feature.randomteleport;

import java.time.Duration;
import java.util.Set;
import org.bukkit.Material;

public interface RandomTeleportSettings {

    Duration randomTeleportTime();

    RandomTeleportRadiusRepresenter randomTeleportRadius();

    RandomTeleportType randomTeleportType();

    String randomTeleportWorld();

    int randomTeleportAttempts();

    Set<Material> unsafeBlocks();

    Set<Material> airBlocks();

    RandomTeleportHeightRangeRepresenter heightRange();


}
