package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0020_Move_godmode_messages_to_dedicated_section extends NamedMigration {
    Migration_0020_Move_godmode_messages_to_dedicated_section() {
        super(
            "Move godmode messages to dedicated section",
            move("player.godEnable", "godmode.godEnable"),
            move("player.godDisable", "godmode.godDisable"),
            move("player.godSetEnable", "godmode.godSetEnable"),
            move("player.godSetDisable", "godmode.godSetDisable")
        );
    }
}
