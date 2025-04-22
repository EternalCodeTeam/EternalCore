package com.eternalcode.core.feature.backpack;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

public class BackpackController implements Listener {

    void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
        Inventory inventory = inventoryCloseEvent.getInventory();
        InventoryView view = inventoryCloseEvent.getView();

        InventoryHolder holder = inventory.getHolder();
        if (holder instanceof BackpackInventory) {

        }
    }
}
