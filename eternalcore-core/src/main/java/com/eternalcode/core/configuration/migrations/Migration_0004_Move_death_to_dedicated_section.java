package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0004_Move_death_to_dedicated_section extends NamedMigration {

    Migration_0004_Move_death_to_dedicated_section() {
        super("Move death to dedicated section",
            move("event.deathMessage", "death.deathMessage"),
            move("event.unknownDeathCause", "death.unknownDeathCause"),
            move("event.deathMessageByDamageCause", "death.deathMessageByDamageCause")
        );
    }

}
