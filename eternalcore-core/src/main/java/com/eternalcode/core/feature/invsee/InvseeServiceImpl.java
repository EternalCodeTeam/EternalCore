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
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
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
            // Clicks and drags are applied by vanilla first, so the GUI state is copied back on the next tick
            gui.setDefaultClickAction(event -> this.scheduler.run(() -> this.applyChanges(gui, targetUuid)));
            gui.setDragAction(event -> this.scheduler.run(() -> this.applyChanges(gui, targetUuid)));
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
    public void refreshInspection(UUID target) {
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
     * Copies the target's inventory into the GUI. Items are put into the inventory directly
     * (not as {@link GuiItem}) so they stay untagged and can be moved freely by the inspector.
     */
    private void fillGui(Gui gui, Inventory targetInventory) {
        Inventory guiInventory = gui.getInventory();

        for (int slot = 0; slot < targetInventory.getSize(); slot++) {
            guiInventory.setItem(slot, cloneOrNull(targetInventory.getItem(slot)));
        }
    }

    private void applyChanges(Gui gui, UUID target) {
        Player targetPlayer = Bukkit.getPlayer(target);

        if (targetPlayer == null) {
            return;
        }

        PlayerInventory targetInventory = targetPlayer.getInventory();
        Inventory guiInventory = gui.getInventory();

        for (int slot = 0; slot < targetInventory.getSize(); slot++) {
            targetInventory.setItem(slot, cloneOrNull(guiInventory.getItem(slot)));
        }

        List<Gui> guis = activeGuis.get(target);

        if (guis == null) {
            return;
        }

        for (Gui otherGui : guis) {
            if (otherGui != gui) {
                this.fillGui(otherGui, targetInventory);
            }
        }
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
            inspectionStatus.remove(target);
        }
    }

    private void updateOrPutInspectionStatus(UUID target) {
        if (!inspectionStatus.containsKey(target)) {
            inspectionStatus.put(target, InvseeStatus.FREE);
        }
    }
}
