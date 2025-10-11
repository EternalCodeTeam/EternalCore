package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0019_Move_speed_messages_to_dedicated_section extends NamedMigration {
    Migration_0019_Move_speed_messages_to_dedicated_section() {
        super(
            "Move speed messages to dedicated section",
            move("player.speedBetweenZeroAndTen", "speed.invalidRange"),
            move("player.speedTypeNotCorrect", "speed.invalidType"),
            move("player.speedWalkSet", "speed.walkSet"),
            move("player.speedFlySet", "speed.flySet"),
            move("player.speedWalkSetBy", "speed.walkSetFor"),
            move("player.speedFlySetBy", "speed.flySetFor")
        );
    }
}

