package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0032_Move_join_quit_messages_to_dedicated_section extends NamedMigration {
    Migration_0032_Move_join_quit_messages_to_dedicated_section() {
        super(
            "Move join and quit messages into dedicated sections",
            move("event.joinMessage", "join.playerJoinedServer"),
            move("event.firstJoinMessage", "join.playerJoinedServerFirstTime"),
            move("event.quitMessage", "quit.playerLeftServer")
        );
    }
}
