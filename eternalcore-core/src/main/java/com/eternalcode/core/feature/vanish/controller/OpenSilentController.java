package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishConfiguration;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

@Controller
public class OpenSilentController implements Listener {

    private final VanishService vanishService;
    private final VanishConfiguration config;

    @Inject
    public OpenSilentController(VanishService vanishService, VanishConfiguration config) {
        this.vanishService = vanishService;
        this.config = config;
    }

    @EventHandler
    void onInteract(PlayerInteractEvent event)  {
        if (!event.hasBlock()) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        if (!this.config.silentOpen) {
            event.setCancelled(true);
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        Material type = clickedBlock.getType();

        if (!this.isContainerType(type)) {
            return;
        }

        Inventory inventory = this.getInventory(clickedBlock.getState());

        if (type == Material.ENDER_CHEST) {
            inventory = player.getEnderChest();
        }

        if (inventory == null) {
            return;
        }

        event.setCancelled(true);

        Inventory cloneInventory = Bukkit.createInventory(null, inventory.getSize(), "Vanish " + type.name());
        cloneInventory.setContents(inventory.getContents());

        player.openInventory(cloneInventory);
    }

    boolean isContainerType(Material type) {
        return type == Material.CHEST
            || type == Material.TRAPPED_CHEST
            || type == Material.BARREL
            || Tag.SHULKER_BOXES.isTagged(type);
    }

    Inventory getInventory(BlockState state) {
        if (state instanceof Chest chest) {
            return chest.getInventory();
        }
        else if (state instanceof Barrel barrel) {
            return barrel.getInventory();
        }
        else if (state instanceof ShulkerBox shulkerBox) {
            return shulkerBox.getInventory();
        }
        return null;
    }
}
