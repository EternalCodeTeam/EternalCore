package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.ConfigMigration;

public class Migrations {

    public static final ConfigMigration[] ALL = new ConfigMigration[] {
        new Migration_0001_Rename_privateChat_to_msg(),
        new Migration_0002_Move_Spawn_Settings_to_spawn_config_section(),
        new Migration_0003_Move_tprp_to_dedicated_section(),
        new Migration_0006_Move_alert_to_broadcast_section(),
        new Migration_0007_Move_clear_to_dedicated_section(),
        new Migration_0008_Move_repair_to_dedicated_section(),
        new Migration_0009_Improve_Homes_Config(),
        new Migration_0010_Move_give_to_dedicated_section(),
        new Migration_0011_Move_enchant_to_dedicated_section(),
        new Migration_0012_Move_repair_argument_messages_to_dedicated_section(),
        new Migration_0013_Move_enchant_messages_to_dedicated_section(),
        new Migration_0014_Move_butcher_argument_messages_to_dedicated_section(),
        new Migration_0016_Move_feed_messages_to_dedicated_section(),
        new Migration_0017_Move_heal_messages_to_dedicated_section(),
        new Migration_0018_Move_kill_messages_to_dedicated_section(),
        new Migration_0019_Move_speed_messages_to_dedicated_section(),
        new Migration_0020_Move_godmode_messages_to_dedicated_section(),
        new Migration_0021_Move_fly_messages_to_dedicated_section(),
        new Migration_0022_Move_ping_messages_to_dedicated_section(),
        new Migration_0023_Move_gamemode_messages_to_dedicated_section(),
        new Migration_0024_Move_online_messages_to_dedicated_section(),
        new Migration_0025_Move_whois_messages_to_dedicated_section(),
        new Migration_0026_Move_butcher_messages_to_dedicated_section(),
        new Migration_0027_Move_give_messages_to_dedicated_section(),
        new Migration_0028_Move_skull_messages_to_dedicated_section(),
        new Migration_0029_Move_enchant_messages_to_dedicated_section(),
        new Migration_0015_Move_ignore_messages_to_dedicated_section(),
        new Migration_0030_Move_back_to_dedicated_section(),
        new Migration_0031_Move_death_messages_to_dedicated_section(),
        new Migration_0032_Move_join_quit_messages_to_dedicated_section(),
        new Migration_0033_Move_disposal_messages_to_dedicated_section(),
        };
}
