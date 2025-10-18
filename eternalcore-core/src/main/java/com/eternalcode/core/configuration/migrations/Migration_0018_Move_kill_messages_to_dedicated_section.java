package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0018_Move_kill_messages_to_dedicated_section extends NamedMigration {
    Migration_0018_Move_kill_messages_to_dedicated_section() {
        super(
            "Move kill messages to dedicated section",
            move("player.killedMessage", "kill.killedTargetPlayer")
        );
    }
}


