package com.eternalcode.core.feature.invsee.listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.invsee.InvseeService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 * Keeps invsee GUIs in sync with the inspected player's inventory.
 * <p>
 * While an inspector's change is pending ({@link com.eternalcode.core.feature.invsee.InvseeStatus#BLOCKED}),
 * the inspected player can't modify their own inventory, so the same item can't end up in both inventories.
 * <p>
 * Most of these events fire before the inventory is actually modified,
 * so the refresh is scheduled for the next tick.
 */
@Controller
public class InvseeInventoryController implements Listener {

    private final InvseeService invseeService;
    private final Scheduler scheduler;

    @Inject
    InvseeInventoryController(InvseeService invseeService, Scheduler scheduler) {
        this.invseeService = invseeService;
        this.scheduler = scheduler;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onDrop(PlayerDropItemEvent event) {
        this.handleChange(event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            this.handleChange(player, event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            this.handleChange(player, event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            this.handleChange(player, event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onConsume(PlayerItemConsumeEvent event) {
        this.handleChange(event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onBlockPlace(BlockPlaceEvent event) {
        this.handleChange(event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onSwapHands(PlayerSwapHandItemsEvent event) {
        this.handleChange(event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onItemDamage(PlayerItemDamageEvent event) {
        this.handleChange(event.getPlayer(), event);
    }

    // Right click in air is fired as already cancelled, but the item is still used, so cancelled events are not ignored
    @EventHandler(priority = EventPriority.HIGHEST)
    void onInteract(PlayerInteractEvent event) {
        this.handleChange(event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onInteractEntity(PlayerInteractEntityEvent event) {
        this.handleChange(event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onItemBreak(PlayerItemBreakEvent event) {
        this.scheduleRefresh(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onArmorChange(PlayerArmorChangeEvent event) {
        this.scheduleRefresh(event.getPlayer());
    }

    // LOWEST, so other plugins already see drops corrected by the pending inspector change
    @EventHandler(priority = EventPriority.LOWEST)
    void onDeath(PlayerDeathEvent event) {
        this.invseeService.closeInspectionsOnDeath(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onQuit(PlayerQuitEvent event) {
        this.invseeService.closeInspections(event.getPlayer().getUniqueId());
    }

    private void handleChange(Player target, Cancellable event) {
        if (this.invseeService.isInventoryBlocked(target.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        this.scheduleRefresh(target);
    }

    private void scheduleRefresh(Player target) {
        UUID targetUuid = target.getUniqueId();

        if (!this.invseeService.isBeingInspected(targetUuid)) {
            return;
        }

        this.scheduler.run(() -> this.invseeService.refreshInspection(targetUuid));
    }
}
