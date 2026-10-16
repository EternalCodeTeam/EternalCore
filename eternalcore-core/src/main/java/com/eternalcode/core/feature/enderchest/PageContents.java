package com.eternalcode.core.feature.enderchest;

import org.bukkit.inventory.ItemStack;

public record PageContents(int page, ItemStack[] items) {

    public boolean isEmpty() {
        for (ItemStack item : this.items) {
            if (item != null) {
                return false;
            }
        }

        return true;
    }
}
