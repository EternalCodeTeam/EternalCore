package com.eternalcode.core.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class ItemUtil {

    public static void removeItem(Player player, Material type, int amount) {
        if (amount <= 0) {
            return;
        }
        PlayerInventory inventory = player.getInventory();

        int size = inventory.getSize();

        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);

            if (is == null) {
                continue;
            }

            if (type != is.getType()) {
                continue;
            }

            int newAmount = is.getAmount() - amount;
            if (newAmount > 0) {
                is.setAmount(newAmount);
                break;
            }

            inventory.clear(slot);

            amount = -newAmount;
            if (amount == 0) {
                break;
            }
        }
    }

    private ItemUtil() {

    }
}
