package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0010_Move_give_to_dedicated_section extends NamedMigration {
    Migration_0010_Move_give_to_dedicated_section() {
        super(
            "Move give to dedicated section",
            move("items.defaultGiveAmount", "give.defaultGiveAmount"),
            move("items.dropOnFullInventory", "give.dropOnFullInventory")
        );
    }
}
