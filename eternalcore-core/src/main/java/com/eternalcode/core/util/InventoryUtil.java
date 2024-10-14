package com.eternalcode.core.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class InventoryUtil {
    public InventoryUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean hasSpace(Inventory inventory, ItemStack itemStack) {
        if (inventory.firstEmpty() != -1) {
            return true;
        }

        for (ItemStack contents : inventory.getContents()) {
            if (contents == null) {
                continue;
            }

            if (!contents.isSimilar(itemStack)) {
                continue;
            }

            if (contents.getMaxStackSize() > contents.getAmount()) {
                return true;
            }
        }

        return false;
    }
}
