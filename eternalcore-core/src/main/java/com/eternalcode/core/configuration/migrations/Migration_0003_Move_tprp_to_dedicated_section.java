package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

class Migration_0003_Move_tprp_to_dedicated_section extends NamedMigration {

    Migration_0003_Move_tprp_to_dedicated_section() {
        super(
            "Move tprp to dedicated config section",
            move("teleport", "teleportToRandomPlayer"),
            move(
                "teleportToRandomPlayer.includeOpPlayersInRandomTeleport",
                "teleportToRandomPlayer.teleportToOpPlayers")
        );
    }
}
