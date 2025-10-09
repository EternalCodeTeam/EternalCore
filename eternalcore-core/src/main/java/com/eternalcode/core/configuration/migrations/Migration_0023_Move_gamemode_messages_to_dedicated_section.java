package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0023_Move_gamemode_messages_to_dedicated_section extends NamedMigration {
    Migration_0023_Move_gamemode_messages_to_dedicated_section() {
        super(
            "Move gamemode messages to dedicated section",
            move("player.gameModeNotCorrect", "gamemode.gameModeNotCorrect"),
            move("player.gameModeMessage", "gamemode.gameModeMessage"),
            move("player.gameModeSetMessage", "gamemode.gameModeSetMessage")
        );
    }
}
