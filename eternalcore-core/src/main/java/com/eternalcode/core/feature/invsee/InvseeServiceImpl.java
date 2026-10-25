package com.eternalcode.core.feature.invsee;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.TranslationManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Service
public class InvseeServiceImpl implements InvseeService {

    private static final String MODIFY_PERMISSION = "eternalcore.invsee.modify";

    private static final HashMap<UUID, UUID> activeInsepctions = new HashMap<>();

    private static final HashMap<UUID, InvseeStatus> inspectionStatus = new HashMap<>();

    private static final HashMap<UUID, List<InvseeInventoryHolder>> activeInventories = new HashMap<>();

    private static final HashMap<UUID, PendingChange> pendingChanges = new HashMap<>();

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;
    private final Scheduler scheduler;
    private final TranslationManager translationManager;
    private final Server server;

    @Inject
    public InvseeServiceImpl(
        NoticeService noticeService,
        MiniMessage miniMessage,
        Scheduler scheduler,
        TranslationManager translationManager,
        Server server
    ) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
        this.scheduler = scheduler;
        this.translationManager = translationManager;
        this.server = server;
    }

    @Override
    public void inspectPlayer(UUID requester, UUID target) {
        Player player = this.server.getPlayer(requester);
        Player targetPlayer = this.server.getPlayer(target);

        if (player == null || targetPlayer == null) {
            return;
        }

        activeInsepctions.put(player.getUniqueId(), targetPlayer.getUniqueId());
        this.updateOrPutInspectionStatus(targetPlayer.getUniqueId());

        PlayerInventory targetInventory = targetPlayer.getInventory();

        String title = this.translationManager.getMessages().invsee().inventoryTitle()
            .replace("{PLAYER}", targetPlayer.getName());

        InvseeInventoryHolder holder = new InvseeInventoryHolder(
            this.server,
            targetPlayer.getUniqueId(),
            player.hasPermission(MODIFY_PERMISSION),
            targetInventory.getSize(),
            this.miniMessage.deserialize(title)
        );

        this.fillInventory(holder, targetInventory);
        activeInventories.computeIfAbsent(targetPlayer.getUniqueId(), uuid -> new ArrayList<>()).add(holder);

        player.openInventory(holder.getInventory());
    }

    @Override
    public boolean isBeingInspected(UUID target) {
        return activeInventories.containsKey(target);
    }

    @Override
    public boolean isInventoryBlocked(UUID target) {
        return inspectionStatus.get(target) == InvseeStatus.BLOCKED;
    }

    @Override
    public void refreshInspection(UUID target) {
        if (this.isInventoryBlocked(target)) {
            return;
        }

        Player targetPlayer = this.server.getPlayer(target);
        List<InvseeInventoryHolder> holders = activeInventories.get(target);

        if (targetPlayer == null || holders == null) {
            return;
        }

        for (InvseeInventoryHolder holder : holders) {
            this.fillInventory(holder, targetPlayer.getInventory());
        }
    }

    @Override
    public void closeInspections(UUID target) {
        this.flushPendingChange(target);

        List<InvseeInventoryHolder> holders = activeInventories.remove(target);

        if (holders == null) {
            return;
        }

        for (InvseeInventoryHolder holder : holders) {
            for (HumanEntity viewer : List.copyOf(holder.getInventory().getViewers())) {
                viewer.closeInventory();
            }
        }

        inspectionStatus.remove(target);
    }

    @Override
    public void closeAllInspections() {
        for (UUID target : List.copyOf(activeInventories.keySet())) {
            this.closeInspections(target);
        }
    }

    @Override
    public void closeInspectionsOnDeath(PlayerDeathEvent event) {
        UUID target = event.getEntity().getUniqueId();
        List<SlotChange> changes = this.flushPendingChange(target);

        if (!event.getKeepInventory()) {
            List<ItemStack> drops = event.getDrops();

            for (SlotChange change : changes) {
                if (change.previous() != null) {
                    drops.remove(change.previous());
                }

                if (change.current() != null && !change.current().containsEnchantment(Enchantment.VANISHING_CURSE)) {
                    drops.add(change.current().clone());
                }
            }
        }

        List<UUID> viewers = new ArrayList<>();

        for (InvseeInventoryHolder holder : activeInventories.getOrDefault(target, List.of())) {
            for (HumanEntity viewer : holder.getInventory().getViewers()) {
                viewers.add(viewer.getUniqueId());
            }
        }

        this.closeInspections(target);

        for (UUID viewer : viewers) {
            this.noticeService.create()
                .player(viewer)
                .notice(translation -> translation.invsee().inspectedPlayerDied())
                .placeholder("{PLAYER}", event.getEntity().getName())
                .send();
        }
    }

    @Override
    public void handleInspectorAction(InvseeInventoryHolder holder, Cancellable event) {
        UUID target = holder.target();
        Player targetPlayer = this.server.getPlayer(target);

        if (targetPlayer == null || this.isInventoryBlocked(target)) {
            event.setCancelled(true);
            return;
        }

        PlayerInventory targetInventory = targetPlayer.getInventory();

        if (!this.matches(holder, targetInventory)) {
            event.setCancelled(true);
            this.fillInventory(holder, targetInventory);
            return;
        }

        inspectionStatus.put(target, InvseeStatus.BLOCKED);
        pendingChanges.put(target, new PendingChange(holder, this.snapshot(holder)));

        this.scheduler.run(() -> this.flushPendingChange(target));
    }

    @Override
    public void closeInspection(InvseeInventoryHolder holder, UUID viewer) {
        activeInsepctions.remove(viewer);

        UUID target = holder.target();
        List<InvseeInventoryHolder> holders = activeInventories.get(target);

        if (holders == null) {
            return;
        }

        holders.remove(holder);

        if (holders.isEmpty()) {
            activeInventories.remove(target);

            if (!pendingChanges.containsKey(target)) {
                inspectionStatus.remove(target);
            }
        }
    }

    private List<SlotChange> flushPendingChange(UUID target) {
        PendingChange pendingChange = pendingChanges.remove(target);

        if (pendingChange == null) {
            return List.of();
        }

        List<SlotChange> changes = this.applyChanges(pendingChange.holder(), pendingChange.snapshot());

        if (activeInventories.containsKey(target)) {
            inspectionStatus.put(target, InvseeStatus.FREE);
        }
        else {
            inspectionStatus.remove(target);
        }

        return changes;
    }

    private List<SlotChange> applyChanges(InvseeInventoryHolder holder, ItemStack[] snapshot) {
        UUID target = holder.target();
        Player targetPlayer = this.server.getPlayer(target);

        if (targetPlayer == null) {
            return List.of();
        }

        PlayerInventory targetInventory = targetPlayer.getInventory();
        Inventory inventory = holder.getInventory();
        List<SlotChange> changes = new ArrayList<>();

        for (int slot = 0; slot < snapshot.length; slot++) {
            ItemStack current = cloneOrNull(inventory.getItem(slot));

            if (!Objects.equals(current, snapshot[slot])) {
                targetInventory.setItem(slot, current);
                changes.add(new SlotChange(snapshot[slot], current));
            }
        }

        for (InvseeInventoryHolder activeHolder : activeInventories.getOrDefault(target, List.of())) {
            this.fillInventory(activeHolder, targetInventory);
        }

        return changes;
    }

    private void fillInventory(InvseeInventoryHolder holder, Inventory targetInventory) {
        Inventory inventory = holder.getInventory();

        for (int slot = 0; slot < holder.contentSize(); slot++) {
            inventory.setItem(slot, cloneOrNull(targetInventory.getItem(slot)));
        }
    }

    private boolean matches(InvseeInventoryHolder holder, Inventory targetInventory) {
        Inventory inventory = holder.getInventory();

        for (int slot = 0; slot < holder.contentSize(); slot++) {
            ItemStack item = cloneOrNull(inventory.getItem(slot));
            ItemStack targetItem = cloneOrNull(targetInventory.getItem(slot));

            if (!Objects.equals(item, targetItem)) {
                return false;
            }
        }

        return true;
    }

    private ItemStack[] snapshot(InvseeInventoryHolder holder) {
        Inventory inventory = holder.getInventory();
        ItemStack[] snapshot = new ItemStack[holder.contentSize()];

        for (int slot = 0; slot < snapshot.length; slot++) {
            snapshot[slot] = cloneOrNull(inventory.getItem(slot));
        }

        return snapshot;
    }

    private static ItemStack cloneOrNull(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            return null;
        }

        return item.clone();
    }

    private void updateOrPutInspectionStatus(UUID target) {
        if (!inspectionStatus.containsKey(target)) {
            inspectionStatus.put(target, InvseeStatus.FREE);
        }
    }

    private record PendingChange(InvseeInventoryHolder holder, ItemStack[] snapshot) {
    }

    private record SlotChange(ItemStack previous, ItemStack current) {
    }
}
