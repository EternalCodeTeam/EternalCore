package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishConfiguration;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

@Controller
class OpenSilentController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;
    private final VanishConfiguration config;

    @Inject
    OpenSilentController(NoticeService noticeService, VanishService vanishService, VanishConfiguration config) {
        this.noticeService = noticeService;
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

        if (!this.config.silentInventoryAccess) {
            event.setCancelled(true);
            this.noticeService.player(player.getUniqueId(), message -> message.vanish().cantOpenInventoryWhileVanished());
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

        Inventory cloneInventory = Bukkit.createInventory(new VanishPreviewInventoryHolder(inventory), inventory.getSize(), "Vanish " + type.name());
        cloneInventory.setContents(inventory.getContents());

        player.openInventory(cloneInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof VanishPreviewInventoryHolder) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof VanishPreviewInventoryHolder) {
            event.setCancelled(true);
        }
    }

    private boolean isContainerType(Material type) {
        return type == Material.CHEST
            || type == Material.TRAPPED_CHEST
            || type == Material.BARREL
            || Tag.SHULKER_BOXES.isTagged(type);
    }

    private Inventory getInventory(BlockState state) {
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

    private static class VanishPreviewInventoryHolder implements InventoryHolder {

        private final Inventory inventory;

        public VanishPreviewInventoryHolder(Inventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public @NotNull Inventory getInventory() {
            return this.inventory;
        }
    }
}
