package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

class Migration_0002_Move_Spawn_Settings_to_spawn_config_section extends NamedMigration {

    Migration_0002_Move_Spawn_Settings_to_spawn_config_section() {
        super(
            "Move spawn settings to spawn config section",
            move("teleport.teleportToSpawnOnDeath", "spawn.teleportToSpawnAfterDeath"),
            move("teleport.teleportToRespawnPoint", "spawn.teleportToPersonalRespawnPoint"),
            move("teleport.teleportTimeToSpawn", "spawn.spawnTeleportTime"),
            move("join.teleportToSpawnOnFirstJoin", "spawn.teleportNewPlayersToSpawn"),
            move("join.teleportToSpawnOnJoin", "spawn.teleportPlayersToSpawnOnJoin")
        );
    }
}
