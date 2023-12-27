package com.eternalcode.core.feature.randomteleport;

public interface RandomTeleportSettings {

    int randomTeleportRadius();

    RandomTeleportType randomTeleportType();

    String randomTeleportWorld();

    int randomTeleportAttempts();
}
