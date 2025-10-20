package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0025_Move_whois_messages_to_dedicated_section extends NamedMigration {
    Migration_0025_Move_whois_messages_to_dedicated_section() {
        super(
            "Move whois messages to dedicated section",
            move("player.whoisCommand", "whois.info")
        );
    }
}


