package com.eternalcode.core.feature.kit.repository;

import com.eternalcode.core.feature.kit.InventoryData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class InventoryAdapter {

    public static InventoryData convert(Inventory inventory) {
        if (inventory == null) {
            return new InventoryData().empty();
        }

        Map<Integer, ItemStack> items = new HashMap<>();

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);

            if (item != null && item.getType() != Material.AIR) {
                items.put(slot, item.clone());
            }
        }

        return new InventoryData(items);
    }

    public static Inventory convert(InventoryData inventoryData) {
        Inventory inventory = Bukkit.createInventory(null, 45);

        if (inventoryData == null || inventoryData.items == null || inventoryData.items.isEmpty()) {
            return inventory;
        }

        for (Map.Entry<Integer, ItemStack> entry : inventoryData.items.entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();

            if (slot >= 0 && slot < inventory.getSize() && item != null) {
                inventory.setItem(slot, item.clone());
            }
        }

        return inventory;
    }
}