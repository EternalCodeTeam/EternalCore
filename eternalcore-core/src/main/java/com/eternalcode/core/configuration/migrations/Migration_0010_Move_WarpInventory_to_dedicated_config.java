package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0010_Move_WarpInventory_to_dedicated_config extends NamedMigration {
    Migration_0010_Move_WarpInventory_to_dedicated_config() {
        super(
            "Move WarpInventory items from messages to dedicated warp-inventory.yml config and clean up old fields",
            move("warpInventory.title", "warpInventory.display.title"),
            move("warpInventory.border.enabled", "warpInventory.border.enabled"),
            move("warpInventory.border.material", "warpInventory.border.material"),
            move("warpInventory.border.fillType", "warpInventory.border.fillType"),
            move("warpInventory.border.name", "warpInventory.border.name"),
            move("warpInventory.border.lore", "warpInventory.border.lore"),
            move("warpInventory.decorationItems.items", "warpInventory.decorationItems.items"),
            move("warpInventory.items", "warpInventory.items")
        );
    }
}
