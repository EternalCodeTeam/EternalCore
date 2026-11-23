package com.eternalcode.core.feature.kit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class KitGuiInventoryHolder implements InventoryHolder {

    private final Inventory inventory;
    private final Map<Integer, Kit> slotToKit = new HashMap<>();

    public KitGuiInventoryHolder(Component title, int size, List<Kit> availableKits) {
        this.inventory = Bukkit.createInventory(this, size, title);

        for (Kit kit : availableKits) {
            int slot = kit.priority();

            // Ensure the slot is valid for the GUI size and the display item exists
            if (slot >= 0 && slot < size && kit.displayItem() != null) {
                this.inventory.setItem(slot, kit.displayItem().clone());
                this.slotToKit.put(slot, kit);
            }
        }
    }

    public Kit kitAtSlot(int slot) {
        return this.slotToKit.get(slot);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}