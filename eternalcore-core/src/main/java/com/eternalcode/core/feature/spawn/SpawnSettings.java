package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.delay.DelaySettings;

import java.time.Duration;

public interface SpawnSettings extends DelaySettings {

    Duration teleportationTimeToSpawn();

    @Override
    default Duration delay() {
        return this.teleportationTimeToSpawn();
    }
}
