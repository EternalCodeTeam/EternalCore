package com.eternalcode.core.feature.spawn;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class SpawnJoinConfig extends OkaeriConfig implements SpawnJoinSettings {


    @Comment({
        "# Delay before teleporting player to spawn (in seconds)",
        "# Set to 0 to teleport instantly"
    })
    public Duration spawnTeleportTime = Duration.ofSeconds(5);

    @Comment({
        "# Teleport to spawn on first join (only once, when the player joins the server for the first time)",
        "# Example: true = teleport new players to spawn automatically"
    })
    public boolean teleportNewPlayersToSpawn = true;

    @Comment({
        "# Teleport to spawn on every join (excluding first join, which is handled above)",
        "# Example: true = always teleport players to spawn when they log in"
    })
    public boolean teleportPlayersToSpawnOnJoin = false;

    @Comment({
        "# Teleport player to spawn after death",
        "# This only works if 'teleportToRespawnPoint' is false, or if 'forceSpawnTeleport' is true"
    })
    public boolean teleportToSpawnAfterDeath = true;

    @Comment({
        "# Teleports player to their personal respawn point (bed, respawn anchor) after death",
        "# If true, overrides 'teleportToSpawnOnDeath' unless 'forceSpawnTeleport' is true"
    })
    public boolean teleportToPersonalRespawnPoint = true;

    @Comment({
        "# Forces teleport to spawn even if player has a personal respawn point",
        "# This overrides 'teleportToPersonalRespawnPoint' setting"
    })
    public boolean alwaysTeleportToSpawnAfterDeath = false;
}
