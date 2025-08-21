package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;
import eu.okaeri.configs.migrate.builtin.NamedMigration;

class Migration_0009_Rename_allowed_to_restricted_jail_commands extends NamedMigration {

    Migration_0009_Rename_allowed_to_restricted_jail_commands() {
        super("Rename allowed to restricted jail commands",
            move("jail.allowedCommands", "jail.restrictedCommands")
        );
    }
}
