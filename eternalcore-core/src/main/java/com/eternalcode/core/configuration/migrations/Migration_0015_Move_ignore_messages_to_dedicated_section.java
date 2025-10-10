package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0015_Move_ignore_messages_to_dedicated_section extends NamedMigration {
    Migration_0015_Move_ignore_messages_to_dedicated_section() {
        super(
            "Move ignore messages to dedicated section",
            move("msg.ignorePlayer", "ignore.playerAdd"),
            move("msg.ignoreAll", "ignore.allAdd"),
            move("msg.cantIgnoreYourself", "ignore.cannotAddSelf"),
            move("msg.alreadyIgnorePlayer", "ignore.alreadyAdded"),
            move("msg.unIgnorePlayer", "ignore.playerRemove"),
            move("msg.unIgnoreAll", "ignore.allRemove"),
            move("msg.cantUnIgnoreYourself", "ignore.cannotRemoveSelf"),
            move("msg.notIgnorePlayer", "ignore.notFound")
        );
    }
}

    Migration_0015_Move_ignore_messages_to_dedicated_section() {
        super(
            "Move ignore messages to dedicated section",
            move("msg.ignorePlayer", "ignore.ignorePlayer"),
            move("msg.ignoreAll", "ignore.ignoreAll"),
            move("msg.cantIgnoreYourself", "ignore.cantIgnoreYourself"),
            move("msg.alreadyIgnorePlayer", "ignore.alreadyIgnorePlayer"),
            move("msg.unIgnorePlayer", "ignore.unIgnorePlayer"),
            move("msg.unIgnoreAll", "ignore.unIgnoreAll"),
            move("msg.cantUnIgnoreYourself", "ignore.cantUnIgnoreYourself"),
            move("msg.notIgnorePlayer", "ignore.notIgnorePlayer")
        );
    }
}
