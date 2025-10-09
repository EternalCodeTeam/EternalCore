package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0028_Move_skull_messages_to_dedicated_section extends NamedMigration {
    Migration_0028_Move_skull_messages_to_dedicated_section() {
        super(
            "Move skull messages to dedicated section",
            move("item.skullMessage", "skull.skullMessage")
        );
    }
}
