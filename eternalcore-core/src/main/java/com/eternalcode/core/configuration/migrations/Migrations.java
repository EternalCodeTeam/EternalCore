package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.ConfigMigration;

public class Migrations {

    public static final ConfigMigration[] ALL = new ConfigMigration[] {
        new Migration_0001_Rename_privateChat_to_msg(),
    };

}
