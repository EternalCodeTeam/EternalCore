package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0019_Move_speed_messages_to_dedicated_section extends NamedMigration {
    Migration_0019_Move_speed_messages_to_dedicated_section() {
        super(
            "Move speed messages to dedicated section",
            move("player.speedBetweenZeroAndTen", "speed.speedBetweenZeroAndTen"),
            move("player.speedTypeNotCorrect", "speed.speedTypeNotCorrect"),
            move("player.speedWalkSet", "speed.speedWalkSet"),
            move("player.speedFlySet", "speed.speedFlySet"),
            move("player.speedWalkSetBy", "speed.speedWalkSetBy"),
            move("player.speedFlySetBy", "speed.speedFlySetBy")
        );
    }
}
