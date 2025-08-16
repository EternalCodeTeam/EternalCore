package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0007_Move_clear_to_dedicated_section extends NamedMigration {

    Migration_0007_Move_clear_to_dedicated_section() {
        super(
            "Move clear to dedicated section",
            move("inventory.inventoryClearMessage", "clear.inventoryCleared"),
            move("inventory.inventoryClearMessageBy", "clear.targetInvenoryCleared")
        );
    }
}
