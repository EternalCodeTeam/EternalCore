package com.eternalcode.core.feature.invsee;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.MaterialUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Service
public class InvseeServiceImpl implements InvseeService {

    private static final String MODIFY_PERMISSION = "eternalcore.invsee.modify";
    private static final int GUI_ROWS = 5;
    private static final Material FILLER_MATERIAL = MaterialUtil.parseRequired(XMaterial.GRAY_STAINED_GLASS_PANE);

    /**
     * First UUID is the player who is inspecting, second UUID is the player being inspected
     */
    private static final HashMap<UUID, UUID> activeInsepctions = new HashMap<>();

    /**
     * First UUID is inspected player, second valie is {@link InvseeStatus} which contains the status of the inspection
     */
    private static final HashMap<UUID, InvseeStatus> inspectionStatus = new HashMap<>();

    /**
     * First UUID is inspected, second field are GUIs of that player's inventory copy
     */
    private static final HashMap<UUID, List<Gui>> activeGuis = new HashMap<>();

    /**
     * First UUID is inspected player, second value is the inspector's change waiting to be written to the inventory
     */
    private static final HashMap<UUID, Runnable> pendingChanges = new HashMap<>();

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;
    private final Scheduler scheduler;

    @Inject
    public InvseeServiceImpl(NoticeService noticeService, MiniMessage miniMessage, Scheduler scheduler) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
        this.scheduler = scheduler;
    }

    @Override
    public void inspectPlayer(UUID requester, UUID target) {
        Player player = Bukkit.getPlayer(requester);
        Player targetPlayer = Bukkit.getPlayer(target);

        if (player == null || targetPlayer == null) {
            return;
        }

        activeInsepctions.put(player.getUniqueId(), targetPlayer.getUniqueId());
        this.updateOrPutInspectionStatus(targetPlayer.getUniqueId());

        Inventory targetInventory = targetPlayer.getInventory();

        Gui gui = Gui.gui()
            .title(miniMessage.deserialize("Inspecting " + targetPlayer.getName()))
            .rows(GUI_ROWS)
            .create();

        UUID targetUuid = targetPlayer.getUniqueId();
        this.setupFiller(gui, targetInventory.getSize());

        if (player.hasPermission(MODIFY_PERMISSION)) {
            gui.setDefaultClickAction(event -> this.handleInspectorAction(gui, targetUuid, event));
            gui.setDragAction(event -> this.handleInspectorAction(gui, targetUuid, event));
        }
        else {
            gui.disableAllInteractions();
        }

        activeGuis.computeIfAbsent(targetUuid, uuid -> new ArrayList<>()).add(gui);

        gui.setCloseGuiAction(event -> {
            activeInsepctions.remove(player.getUniqueId());
            this.removeGui(targetUuid, gui);
        });

        gui.open(player);
        this.fillGui(gui, targetInventory);
    }

    @Override
    public boolean isBeingInspected(UUID target) {
        return activeGuis.containsKey(target);
    }

    @Override
    public boolean isInventoryBlocked(UUID target) {
        return inspectionStatus.get(target) == InvseeStatus.BLOCKED;
    }

    @Override
    public void refreshInspection(UUID target) {
        // While blocked, the pending inspector change refreshes all GUIs after it is written
        if (this.isInventoryBlocked(target)) {
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);
        List<Gui> guis = activeGuis.get(target);

        if (targetPlayer == null || guis == null) {
            return;
        }

        for (Gui gui : guis) {
            this.fillGui(gui, targetPlayer.getInventory());
        }
    }

    @Override
    public void closeInspections(UUID target) {
        // Write the pending change while the player is still online, otherwise the moved item would be duplicated
        this.flushPendingChange(target);

        List<Gui> guis = activeGuis.remove(target);

        if (guis == null) {
            return;
        }

        for (Gui gui : List.copyOf(guis)) {
            for (HumanEntity viewer : List.copyOf(gui.getInventory().getViewers())) {
                viewer.closeInventory();
            }
        }

        inspectionStatus.remove(target);
    }

    /**
     * Runs before vanilla applies the inspector's click or drag. Only one change per inventory
     * is allowed at a time - the inventory stays {@link InvseeStatus#BLOCKED} until the change is written
     * on the next tick, so other inspectors and the target itself can't take the same item.
     */
    private void handleInspectorAction(Gui gui, UUID target, Cancellable event) {
        Player targetPlayer = Bukkit.getPlayer(target);

        if (targetPlayer == null || this.isInventoryBlocked(target)) {
            event.setCancelled(true);
            return;
        }

        PlayerInventory targetInventory = targetPlayer.getInventory();
        Inventory guiInventory = gui.getInventory();

        // The inventory changed since the GUI was filled (e.g. the target picked up an item this tick)
        if (!this.matches(guiInventory, targetInventory)) {
            event.setCancelled(true);
            this.fillGui(gui, targetInventory);
            return;
        }

        inspectionStatus.put(target, InvseeStatus.BLOCKED);

        ItemStack[] snapshot = this.snapshot(guiInventory, targetInventory.getSize());
        pendingChanges.put(target, () -> this.applyChanges(gui, target, snapshot));

        this.scheduler.run(() -> this.flushPendingChange(target));
    }

    private void flushPendingChange(UUID target) {
        Runnable pendingChange = pendingChanges.remove(target);

        if (pendingChange == null) {
            return;
        }

        pendingChange.run();

        if (activeGuis.containsKey(target)) {
            inspectionStatus.put(target, InvseeStatus.FREE);
        }
        else {
            inspectionStatus.remove(target);
        }
    }

    /**
     * Writes only the slots the inspector changed, so changes made to other slots in the meantime are not reverted.
     */
    private void applyChanges(Gui gui, UUID target, ItemStack[] snapshot) {
        Player targetPlayer = Bukkit.getPlayer(target);

        if (targetPlayer == null) {
            return;
        }

        PlayerInventory targetInventory = targetPlayer.getInventory();
        Inventory guiInventory = gui.getInventory();

        for (int slot = 0; slot < snapshot.length; slot++) {
            ItemStack current = cloneOrNull(guiInventory.getItem(slot));

            if (!Objects.equals(current, snapshot[slot])) {
                targetInventory.setItem(slot, current);
            }
        }

        List<Gui> guis = activeGuis.get(target);

        if (guis == null) {
            return;
        }

        for (Gui activeGui : guis) {
            this.fillGui(activeGui, targetInventory);
        }
    }

    /**
     * Copies the target's inventory into the GUI. Items are put into the inventory directly
     * (not as {@link GuiItem}) so they stay untagged and can be moved freely by the inspector.
     */
    private void fillGui(Gui gui, Inventory targetInventory) {
        Inventory guiInventory = gui.getInventory();

        for (int slot = 0; slot < targetInventory.getSize(); slot++) {
            guiInventory.setItem(slot, cloneOrNull(targetInventory.getItem(slot)));
        }
    }

    private boolean matches(Inventory guiInventory, Inventory targetInventory) {
        for (int slot = 0; slot < targetInventory.getSize(); slot++) {
            ItemStack guiItem = cloneOrNull(guiInventory.getItem(slot));
            ItemStack targetItem = cloneOrNull(targetInventory.getItem(slot));

            if (!Objects.equals(guiItem, targetItem)) {
                return false;
            }
        }

        return true;
    }

    private ItemStack[] snapshot(Inventory guiInventory, int size) {
        ItemStack[] snapshot = new ItemStack[size];

        for (int slot = 0; slot < size; slot++) {
            snapshot[slot] = cloneOrNull(guiInventory.getItem(slot));
        }

        return snapshot;
    }

    /**
     * Blocks the slots after the player's inventory (41-44), so items can't be placed there.
     */
    private void setupFiller(Gui gui, int playerInventorySize) {
        GuiItem filler = ItemBuilder.from(FILLER_MATERIAL)
            .name(Component.empty())
            .asGuiItem(event -> event.setCancelled(true));

        for (int slot = playerInventorySize; slot < GUI_ROWS * 9; slot++) {
            gui.setItem(slot, filler);
        }
    }

    private static ItemStack cloneOrNull(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            return null;
        }

        return item.clone();
    }

    private void removeGui(UUID target, Gui gui) {
        List<Gui> guis = activeGuis.get(target);

        if (guis == null) {
            return;
        }

        guis.remove(gui);

        if (guis.isEmpty()) {
            activeGuis.remove(target);

            // Keep the BLOCKED status until the pending change is written
            if (!pendingChanges.containsKey(target)) {
                inspectionStatus.remove(target);
            }
        }
    }

    private void updateOrPutInspectionStatus(UUID target) {
        if (!inspectionStatus.containsKey(target)) {
            inspectionStatus.put(target, InvseeStatus.FREE);
        }
    }
}
