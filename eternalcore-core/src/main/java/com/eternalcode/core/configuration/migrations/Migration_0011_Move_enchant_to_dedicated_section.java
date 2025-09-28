package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0011_Move_enchant_to_dedicated_section extends NamedMigration {
    Migration_0011_Move_enchant_to_dedicated_section() {
        super(
            "Move enchant to dedicated section",
            move("items.unsafeEnchantments", "enchant.unsafeEnchantments")
        );
    }
}
