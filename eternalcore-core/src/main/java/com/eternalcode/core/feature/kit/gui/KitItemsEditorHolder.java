package com.eternalcode.core.feature.kit.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

final class KitItemsEditorHolder implements InventoryHolder {

    private final String kitName;
    private Inventory inventory;

    KitItemsEditorHolder(String kitName) {
        this.kitName = kitName;
    }

    String getKitName() {
        return this.kitName;
    }

    void attach(Inventory inventory) {
        if (this.inventory != null) {
            throw new IllegalStateException("Inventory already attached to editor of kit " + this.kitName);
        }

        this.inventory = inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
