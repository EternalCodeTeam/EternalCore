package com.eternalcode.core.feature.randomteleport;

import java.util.Set;
import org.bukkit.Material;

public interface RandomTeleportSettings {

    RandomTeleportRadiusRepresenter randomTeleportRadius();

    RandomTeleportType randomTeleportType();

    String randomTeleportWorld();

    int randomTeleportAttempts();

    Set<Material> unsafeBlocks();

    Set<Material> airBlocks();

    int minHeight();


}
