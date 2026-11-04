package com.eternalcode.core.feature.kit;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Controller
public class KitEditListener implements Listener {

    private final KitService kitService;
    private final NoticeService noticeService;

    @Inject
    public KitEditListener(KitService kitService, NoticeService noticeService) {
        this.kitService = kitService;
        this.noticeService = noticeService;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof KitEditInventoryHolder holder)) {
            return;
        }

        if (holder.isLockedSlot(event.getRawSlot())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof KitEditInventoryHolder holder)) {
            return;
        }

        Inventory topInventory = event.getInventory();
        Kit kit = holder.kit();

        Inventory mappedInventory = Bukkit.createInventory(null, 45);

        for (int guiSlot = 0; guiSlot < topInventory.getSize(); guiSlot++) {
            int playerSlot = holder.playerSlot(guiSlot);

            if (playerSlot != KitEditInventoryHolder.NO_PLAYER_SLOT) {
                ItemStack item = topInventory.getItem(guiSlot);

                if (item != null && item.getType() != Material.AIR) {
                    mappedInventory.setItem(playerSlot, item);
                }
            }
        }

        this.kitService.updateKitInventory(kit.name(), mappedInventory);

        this.noticeService.create()
            .notice(translation -> translation.kit().kitSaved())
            .placeholder("{KIT}", kit.name())
            .player(event.getPlayer().getUniqueId())
            .send();
    }
}