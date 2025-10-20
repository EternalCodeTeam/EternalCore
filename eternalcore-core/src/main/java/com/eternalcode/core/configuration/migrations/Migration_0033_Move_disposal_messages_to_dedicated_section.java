package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0033_Move_disposal_messages_to_dedicated_section extends NamedMigration {
    Migration_0033_Move_disposal_messages_to_dedicated_section() {
        super(
            "Move disposal messages into dedicated sections",
            move("inventory.disposalTitle", "disposal.disposalTitle")
        );
    }
}
