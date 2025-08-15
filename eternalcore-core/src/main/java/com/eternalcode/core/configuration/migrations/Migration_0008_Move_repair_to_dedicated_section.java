package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0008_Move_repair_to_dedicated_section extends NamedMigration {

    Migration_0008_Move_repair_to_dedicated_section() {
        super(
            "Move repair to dedicated section",
            move("item.repairMessage", "repair.itemRepaired"),
            move("item.repairAllMessage", "repair.allItemsRepaired"),
            move("item.repairDelayMessage", "repair.delay")
        );
    }
}
