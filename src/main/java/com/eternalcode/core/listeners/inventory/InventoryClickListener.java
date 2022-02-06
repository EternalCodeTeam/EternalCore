package com.eternalcode.core.listeners.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Component title = event.getView().title();

        if (title.contains(Component.text("Armor player: "))) {
            ItemStack itemStack = event.getCurrentItem();

            if (itemStack != null && itemStack.getType() == Material.GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
            }
        }
    }
}
