package com.eternalcode.core.feature.invsee.listeners;

import com.eternalcode.core.feature.invsee.InvseeInventoryHolder;
import com.eternalcode.core.feature.invsee.InvseeService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

@Controller
public class InvseeGuiController implements Listener {

    private final InvseeService invseeService;

    @Inject
    InvseeGuiController(InvseeService invseeService) {
        this.invseeService = invseeService;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    void onClick(InventoryClickEvent event) {
        if (!(this.getHolder(event.getInventory()) instanceof InvseeInventoryHolder holder)) {
            return;
        }

        if (!holder.isEditable()
            || holder.isFillerSlot(event.getRawSlot())
            || InvseeInventoryHolder.isFiller(event.getCurrentItem())
            || InvseeInventoryHolder.isFiller(event.getCursor())
        ) {
            event.setCancelled(true);
            return;
        }

        if (event.getClickedInventory() == null) {
            return;
        }

        this.invseeService.handleInspectorAction(holder, event);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    void onDrag(InventoryDragEvent event) {
        if (!(this.getHolder(event.getInventory()) instanceof InvseeInventoryHolder holder)) {
            return;
        }

        if (!holder.isEditable() || event.getRawSlots().stream().anyMatch(holder::isFillerSlot)) {
            event.setCancelled(true);
            return;
        }

        this.invseeService.handleInspectorAction(holder, event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onClose(InventoryCloseEvent event) {
        if (this.getHolder(event.getInventory()) instanceof InvseeInventoryHolder holder) {
            this.invseeService.closeInspection(holder, event.getPlayer().getUniqueId());
        }
    }

    @Subscribe
    void onShutdown(EternalShutdownEvent event) {
        this.invseeService.closeAllInspections();
    }

    private Object getHolder(Inventory inventory) {
        return inventory.getHolder(false);
    }
}
