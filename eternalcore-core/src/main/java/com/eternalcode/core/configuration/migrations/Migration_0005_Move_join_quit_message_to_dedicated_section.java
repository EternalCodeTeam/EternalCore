package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0005_Move_join_quit_message_to_dedicated_section extends NamedMigration {

    Migration_0005_Move_join_quit_message_to_dedicated_section() {
        super("Move joinquit messages from event to joinQuit section",
            move("event.joinMessage", "joinQuit.joinMessage"),
            move("event.firstJoinMessage", "joinQuit.firstJoinMessage"),
            move("event.quitMessage", "joinQuit.quitMessage")
        );
    }

}
