package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0021_Move_fly_messages_to_dedicated_section extends NamedMigration {
    Migration_0021_Move_fly_messages_to_dedicated_section() {
        super(
            "Move fly messages to dedicated section",
            move("player.flyEnable", "fly.flyEnabled"),
            move("player.flyDisable", "fly.flyDisabled"),
            move("player.flySetEnable", "fly.flyEnabledForTarget"),
            move("player.flySetDisable", "fly.flyDisabledForTarget")
        );
    }
}


