package com.eternalcode.core.listeners.inventory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        String title = event.getView().getTitle();

        if (title.contains("Armor player: ")){
            ItemStack itemStack = event.getCurrentItem();

            if (itemStack != null && itemStack.getType() == Material.GRAY_STAINED_GLASS_PANE){
                event.setCancelled(true);
            }
        }
    }
}
