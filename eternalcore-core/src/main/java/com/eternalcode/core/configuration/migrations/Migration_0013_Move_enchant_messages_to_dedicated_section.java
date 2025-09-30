package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0013_Move_enchant_messages_to_dedicated_section extends NamedMigration {
    Migration_0013_Move_enchant_messages_to_dedicated_section() {
        super(
            "Move enchant messages to dedicated section",
            move("argument.noEnchantment", "enchant.invalidEnchantment"),
            move("argument.noValidEnchantmentLevel", "enchant.invalidEnchantmentLevel")
        );
    }
}
