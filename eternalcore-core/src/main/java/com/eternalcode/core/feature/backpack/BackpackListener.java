package com.eternalcode.core.feature.backpack;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

@Controller
public class BackpackListener implements Listener {

    private final BackpackService backpackService;

    @Inject
    public BackpackListener(BackpackService backpackService) {
        this.backpackService = backpackService;
    }

    @EventHandler
    void onInvenotoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof BackpackInventory backpackInventory)) {
            return;
        }
        HashMap<Integer, ItemStack> newMap = new HashMap<>();
        if (inventory.isEmpty()) {
            return;
        }

        for (int i = 0; i <=35; i++ ) {
            ItemStack item = inventory.getItem(i);
            if (item != null && !item.getType().equals(Material.AIR)) {
                newMap.put(i, item);
            }
        }

        if (event.getPlayer() instanceof Player player) {
            UUID uniqueId = player.getUniqueId();

            
            Backpack oldBackpack = backpackInventory.getOldBackpack();
            Backpack backpack = new BackpackImpl(oldBackpack.getUuid(), oldBackpack.getOwner())

            this.backpackService.updateBackpack()

            this.cache.put(uniqueId, newMap);
            JSONObject json = new JSONObject(newMap);

            this.database.saveBackpack(uniqueId, json);
            this.cache.remove(uniqueId);
        }



    }

}
