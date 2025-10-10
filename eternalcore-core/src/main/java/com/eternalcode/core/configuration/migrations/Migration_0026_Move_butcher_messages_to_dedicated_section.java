package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0026_Move_butcher_messages_to_dedicated_section extends NamedMigration {
    Migration_0026_Move_butcher_messages_to_dedicated_section() {
        super(
            "Move butcher messages to dedicated section",
            move("player.butcherCommand", "butcher.killed"),
            move("player.safeChunksMessage", "butcher.limitExceeded")
        );
    }
}

