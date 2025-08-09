package com.eternalcode.core.feature.spawn;

import java.time.Duration;

public interface SpawnJoinSettings {
    Duration spawnTeleportTime();
    boolean teleportNewPlayersToSpawn();
    boolean teleportPlayersToSpawnOnJoin();
    boolean teleportToSpawnAfterDeath();
    boolean teleportToPersonalRespawnPoint();
    boolean alwaysTeleportToSpawnAfterDeath();
}
