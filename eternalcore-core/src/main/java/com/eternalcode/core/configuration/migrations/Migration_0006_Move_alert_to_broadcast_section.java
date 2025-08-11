package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0006_Move_alert_to_broadcast_section extends NamedMigration {

    Migration_0006_Move_alert_to_broadcast_section() {
        super(
            "Move alert to broadcast section",
            move("chat.alertMessageFormat", "broadcast.messageFormat")
        );
    }
}
