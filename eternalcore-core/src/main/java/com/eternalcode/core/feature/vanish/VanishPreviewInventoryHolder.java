package com.eternalcode.core.feature.vanish;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class VanishPreviewInventoryHolder implements InventoryHolder {

    private final Inventory inventory;

    public VanishPreviewInventoryHolder(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
