package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0030_Move_back_to_dedicated_section extends NamedMigration {
    Migration_0030_Move_back_to_dedicated_section() {
        super(
            "Improve homes config",
            move("teleport.teleportedToLastLocation", "back.teleportedToLastTeleportLocation"),
            move("teleport.teleportedSpecifiedPlayerLastLocation", "back.teleportedOtherToLastTeleportLocation"),
            move("teleport.lastLocationNoExist", "back.lastLocationNotFound")
        );
    }
}

