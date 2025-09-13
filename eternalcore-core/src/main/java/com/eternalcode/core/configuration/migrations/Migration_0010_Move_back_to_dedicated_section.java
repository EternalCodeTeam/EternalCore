package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0010_Move_back_to_dedicated_section extends NamedMigration {
    Migration_0010_Move_back_to_dedicated_section() {
        super(
            "Improve homes config",
            move("teleport.teleportedToLastLocation", "back.lastLocationNoExist"),
            move("teleport.teleportedSpecifiedPlayerLastLocation", "back.teleportedSpecifiedPlayerLastLocation"),
            move("teleport.lastLocationNoExist", "back.lastLocationNoExist")
        );
    }
}

