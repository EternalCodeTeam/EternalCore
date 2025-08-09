package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.ConfigMigration;

public class Migrations {

    public static final ConfigMigration[] ALL = new ConfigMigration[] {
        new Migration_0001_Rename_privateChat_to_msg(),
        new Migration_0004_Move_death_to_dedicated_section(),
        new Migration_0005_Move_join_quit_message_to_dedicated_section()
    };

}
