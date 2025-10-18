package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0019_Move_speed_messages_to_dedicated_section extends NamedMigration {
    Migration_0019_Move_speed_messages_to_dedicated_section() {
        super(
            "Move speed messages to dedicated section",
            move("player.speedBetweenZeroAndTen", "speed.invalidSpeedRange"),
            move("player.speedTypeNotCorrect", "speed.invalidSpeedType"),
            move("player.speedWalkSet", "speed.walkSpeedSetForYourself"),
            move("player.speedFlySet", "speed.flySpeedSetForYourself"),
            move("player.speedWalkSetBy", "speed.walkSpeedSetForTargetPlayer"),
            move("player.speedFlySetBy", "speed.flySpeedSetForTargetPlayer")
        );
    }
}

