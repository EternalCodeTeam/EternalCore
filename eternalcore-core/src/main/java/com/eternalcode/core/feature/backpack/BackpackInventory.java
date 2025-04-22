package com.eternalcode.core.feature.backpack;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BackpackInventory implements InventoryHolder {

    private final Inventory inventory;
    private final Backpack oldBackpack;

    public BackpackInventory(Plugin plugin, Player player, Integer size, String title, Backpack backpack) {
        this.inventory = plugin.getServer().createInventory(player, size, title);
        this.oldBackpack = backpack;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public Backpack getOldBackpack() {
        return oldBackpack;
    }
}
