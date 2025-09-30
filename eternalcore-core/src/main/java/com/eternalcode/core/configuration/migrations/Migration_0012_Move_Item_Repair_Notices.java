package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0012_Move_Item_Repair_Notices extends NamedMigration {
    Migration_0012_Move_Item_Repair_Notices() {
        super(
            "Move repair notices to dedicated section",
            move("argument.noDamaged", "repair.cannotRepair"),
            move("argument.noDamagedItems", "repair.needDamagedItem")
        );
    }
}
