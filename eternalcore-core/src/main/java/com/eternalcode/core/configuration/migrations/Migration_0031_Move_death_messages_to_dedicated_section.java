package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0031_Move_death_messages_to_dedicated_section extends NamedMigration {
    Migration_0031_Move_death_messages_to_dedicated_section() {
        super(
            "Move death messages to dedicated section",
            move("event.deathMessage", "deathMessage.playerKilledByOtherPlayer"),
            move("event.deathMessageByDamageCause", "deathMessage.playerDiedByDamageCause"),
            move("event.unknownDeathCause", "deathMessage.playerDiedByUnknownCause"),
            move("event.deathMessageByEntity", "deathMessage.playerKilledByEntity"),
            move("event.deathMessageByMobType", "deathMessage.playerKilledByMobType")
        );
    }
}
