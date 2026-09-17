package com.eternalcode.core.feature.deathteleport;

import java.time.Duration;

public interface DeathTeleportSettings {

    boolean deathTeleportEnabled();

    Duration deathTeleportDelay();

    Duration deathLocationCacheDuration();
}
