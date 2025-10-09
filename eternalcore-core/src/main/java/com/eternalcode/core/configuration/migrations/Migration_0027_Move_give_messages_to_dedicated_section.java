package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0027_Move_give_messages_to_dedicated_section extends NamedMigration {
    Migration_0027_Move_give_messages_to_dedicated_section() {
        super(
            "Move give messages to dedicated section",
            move("item.giveReceived", "give.giveReceived"),
            move("item.giveGiven", "give.giveGiven"),
            move("item.giveNoSpace", "give.giveNoSpace"),
            move("item.giveNotItem", "give.giveNotItem")
        );
    }
}
