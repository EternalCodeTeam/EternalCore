package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

@Controller
class KitItemsEditorController implements Listener {

    private final KitItemsEditor itemsEditor;

    @Inject
    KitItemsEditorController(KitItemsEditor itemsEditor) {
        this.itemsEditor = itemsEditor;
    }

    @EventHandler
    void onClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof KitItemsEditorHolder holder)) {
            return;
        }

        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        this.itemsEditor.save(player, holder);
    }
}
