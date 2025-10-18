package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0017_Move_heal_messages_to_dedicated_section extends NamedMigration {
    Migration_0017_Move_heal_messages_to_dedicated_section() {
        super(
            "Move heal messages to dedicated section",
            move("player.healMessage", "heal.healedYourself"),
            move("player.healMessageBy", "heal.healedTargetPlayer")
        );
    }
}


