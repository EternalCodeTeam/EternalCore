package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0016_Move_feed_messages_to_dedicated_section extends NamedMigration {
    Migration_0016_Move_feed_messages_to_dedicated_section() {
        super(
            "Move feed messages to dedicated section",
            move("player.feedMessage", "feed.received"),
            move("player.feedMessageBy", "feed.given")
        );
    }
}


