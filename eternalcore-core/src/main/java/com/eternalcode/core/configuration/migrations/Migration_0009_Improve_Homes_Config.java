package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0009_Improve_Homes_Config extends NamedMigration {
    Migration_0009_Improve_Homes_Config() {
        super(
            "Improve homes config",
            move("homes.teleportTimeToHomes", "homes.delay"),
            move("homes.defaultHomeName", "homes.defaultName")
        );
    }
}

