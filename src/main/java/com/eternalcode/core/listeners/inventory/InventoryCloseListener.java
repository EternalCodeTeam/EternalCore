package com.eternalcode.core.listeners.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import panda.std.Option;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(org.bukkit.event.inventory.InventoryCloseEvent event){
        String title = event.getView().getTitle();

        if (title.contains("Armor player: ")){
            String playerString = title.replace("Armor player: ", "");
            Option<Player> playerOption = Option.of(Bukkit.getPlayer(playerString));

            playerOption.peek(player -> {
                player.getInventory().setHelmet(event.getInventory().getItem(3));
                player.getInventory().setChestplate(event.getInventory().getItem(2));
                player.getInventory().setLeggings(event.getInventory().getItem(1));
                player.getInventory().setBoots(event.getInventory().getItem(0));
                player.updateInventory();
            });
        }
    }

}
