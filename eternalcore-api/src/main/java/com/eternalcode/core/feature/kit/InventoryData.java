package com.eternalcode.core.feature.kit;

import org.bukkit.inventory.ItemStack;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InventoryData implements Serializable {

    public Map<Integer, ItemStack> items = new HashMap<>();

    public InventoryData() {
    }

    public InventoryData(Map<Integer, ItemStack> items) {
        this.items = items;
    }

    public InventoryData empty() {
        return new InventoryData(new HashMap<>());
    }
}