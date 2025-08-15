package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.ConfigMigration;

public class Migrations {

    public static final ConfigMigration[] ALL = new ConfigMigration[] {
        new Migration_0001_Rename_privateChat_to_msg(),
        new Migration_0002_Move_Spawn_Settings_to_spawn_config_section(),
        new Migration_0003_Move_tprp_to_dedicated_section(),
        new Migration_0006_Move_alert_to_broadcast_section(),
        new Migration_0007_Move_clear_to_dedicated_section(),
        new Migrations_0008_Move_repair_to_dedicated_section()
    };

}
