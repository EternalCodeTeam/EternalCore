package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0029_Move_enchant_messages_to_dedicated_section extends NamedMigration {
    Migration_0029_Move_enchant_messages_to_dedicated_section() {
        super(
            "Move enchant messages to dedicated section",
            move("item.enchantedMessage", "enchant.enchantedItem"),
            move("item.enchantedMessageFor", "enchant.enchantedTargetPlayerItem"),
            move("item.enchantedMessageBy", "enchant.enchantedItemByAdmin"),
            move("item.invalidEnchantment", "enchant.enchantmentNotFound"),
            move("item.invalidEnchantmentLevel", "enchant.enchantmentLevelUnsupported")
        );
    }
}
