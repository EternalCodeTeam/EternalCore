package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0014_Move_butcher_messages_to_dedicated_section extends NamedMigration {
    Migration_0014_Move_butcher_messages_to_dedicated_section() {
        super(
            "Move butcher messages to dedicated section",
            move("argument.incorrectNumberOfChunks", "butcher.incorrectNumberOfChunks")
        );
    }
}
