package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0027_Move_give_messages_to_dedicated_section extends NamedMigration {
    Migration_0027_Move_give_messages_to_dedicated_section() {
        super(
            "Move give messages to dedicated section",
            move("item.giveReceived", "give.received"),
            move("item.giveGiven", "give.given"),
            move("item.giveNoSpace", "give.noSpace"),
            move("item.giveNotItem", "give.invalidItem")
        );
    }
}
