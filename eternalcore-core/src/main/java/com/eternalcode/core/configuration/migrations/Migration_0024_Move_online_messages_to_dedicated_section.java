package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0024_Move_online_messages_to_dedicated_section extends NamedMigration {
    Migration_0024_Move_online_messages_to_dedicated_section() {
        super(
            "Move online messages to dedicated section",
            move("player.onlinePlayersCountMessage", "online.onlinePlayersCount"),
            move("player.onlinePlayersMessage", "online.onlinePlayersList"),
            move("player.fullServerSlots", "online.serverFull")
        );
    }
}


