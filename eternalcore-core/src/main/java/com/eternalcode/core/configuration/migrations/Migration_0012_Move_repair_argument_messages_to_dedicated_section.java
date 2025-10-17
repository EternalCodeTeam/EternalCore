package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0012_Move_repair_argument_messages_to_dedicated_section extends NamedMigration {
    Migration_0012_Move_repair_argument_messages_to_dedicated_section() {
        super(
            "Move repair argument messages to dedicated section",
            move("argument.noDamaged", "repair.cannotRepair"),
            move("argument.noDamagedItems", "repair.needDamagedItem")
        );
    }
}
