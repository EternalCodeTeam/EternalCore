package com.eternalcode.core.feature.spawn;

public interface SpawnJoinSettings {
    boolean teleportNewPlayersToSpawn();
    boolean teleportPlayersToSpawnOnJoin();
    boolean teleportToSpawnAfterDeath();
    boolean teleportToPersonalRespawnPoint();
    boolean alwaysTeleportToSpawnAfterDeath();
}
