package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0024_Move_online_messages_to_dedicated_section extends NamedMigration {
    Migration_0024_Move_online_messages_to_dedicated_section() {
        super(
            "Move online messages to dedicated section",
            move("player.onlinePlayersCountMessage", "online.onlinePlayersCountMessage"),
            move("player.onlinePlayersMessage", "online.onlinePlayersMessage"),
            move("player.fullServerSlots", "online.fullServerSlots")
        );
    }
}
