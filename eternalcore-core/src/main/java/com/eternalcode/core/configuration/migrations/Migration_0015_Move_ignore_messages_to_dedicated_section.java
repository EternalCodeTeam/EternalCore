package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0015_Move_ignore_messages_to_dedicated_section extends NamedMigration {
    Migration_0015_Move_ignore_messages_to_dedicated_section() {
        super(
            "Move ignore messages to dedicated section",
            move("msg.ignorePlayer", "ignore.playerIgnored"),
            move("msg.ignoreAll", "ignore.allPlayersIgnored"),
            move("msg.cantIgnoreYourself", "ignore.cannotIgnoreSelf"),
            move("msg.alreadyIgnorePlayer", "ignore.playerAlreadyIgnored"),
            move("msg.unIgnorePlayer", "ignore.playerUnignored"),
            move("msg.unIgnoreAll", "ignore.allPlayersUnignored"),
            move("msg.cantUnIgnoreYourself", "ignore.cannotUnignoreSelf"),
            move("msg.notIgnorePlayer", "ignore.playerNotIgnored")
        );
    }
}
