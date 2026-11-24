package com.eternalcode.core.feature.kit.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Marks an inventory as a kit items editor. Plain Bukkit inventory on purpose - GUI libraries
 * tag their items (e.g. Triumph's "mf-gui" PDC key), which would end up saved inside the kit.
 */
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
