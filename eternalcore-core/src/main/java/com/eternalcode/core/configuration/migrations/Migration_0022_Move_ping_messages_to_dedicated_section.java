package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0022_Move_ping_messages_to_dedicated_section extends NamedMigration {
    Migration_0022_Move_ping_messages_to_dedicated_section() {
        super(
            "Move ping messages to dedicated section",
            move("player.pingMessage", "ping.self"),
            move("player.pingOtherMessage", "ping.other")
        );
    }
}


