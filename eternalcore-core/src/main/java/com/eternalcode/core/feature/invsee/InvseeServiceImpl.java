package com.eternalcode.core.feature.invsee;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Service
public class InvseeServiceImpl implements InvseeService {
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

    @Inject
    public InvseeServiceImpl(NoticeService noticeService, MiniMessage miniMessage) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
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
            .rows(5)
            .create();

        this.fillGui(gui, targetInventory);

        UUID targetUuid = targetPlayer.getUniqueId();
        activeGuis.computeIfAbsent(targetUuid, uuid -> new ArrayList<>()).add(gui);

        gui.setCloseGuiAction(event -> {
            activeInsepctions.remove(player.getUniqueId());
            this.removeGui(targetUuid, gui);
        });

        gui.open(player);
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
            gui.update();
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

    private void fillGui(Gui gui, Inventory targetInventory) {
        for (int slot = 0; slot < targetInventory.getSize(); slot++) {
            ItemStack item = targetInventory.getItem(slot);

            if (item == null || item.getType().isAir()) {
                gui.removeItem(slot);
                continue;
            }

            gui.setItem(slot, new GuiItem(item));
        }
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
