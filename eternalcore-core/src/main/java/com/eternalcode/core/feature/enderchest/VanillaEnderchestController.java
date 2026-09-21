package com.eternalcode.core.feature.enderchest;

import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

@Controller
class VanillaEnderchestController implements Listener {

    private final Map<UUID, Location> openLids = new ConcurrentHashMap<>();

    private final EnderchestInventory enderchestInventory;
    private final EnderchestSettings settings;
    private final NoticeService noticeService;
    private final MinecraftScheduler scheduler;

    @Inject
    VanillaEnderchestController(
        EnderchestInventory enderchestInventory,
        EnderchestSettings settings,
        NoticeService noticeService,
        MinecraftScheduler scheduler
    ) {
        this.enderchestInventory = enderchestInventory;
        this.settings = settings;
        this.noticeService = noticeService;
        this.scheduler = scheduler;
    }

    @EventHandler(ignoreCancelled = true)
    void onOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        Inventory inventory = event.getInventory();

        if (this.enderchestInventory.isOpenPage(inventory)) {
            this.setLid(this.openLids.get(player.getUniqueId()), true);
            return;
        }

        if (inventory.getType() != InventoryType.ENDER_CHEST) {
            return;
        }

        if (this.settings.enderchestsBlocked()) {
            event.setCancelled(true);
            this.noticeService.create()
                .notice(translation -> translation.enderchest().enderchestsBlocked())
                .player(player.getUniqueId())
                .send();
            return;
        }

        if (!this.settings.replaceVanillaEnderchest()) {
            return;
        }

        InventoryHolder holder = inventory.getHolder();
        OfflinePlayer chestOwner = holder instanceof Player holderPlayer ? holderPlayer : player;

        event.setCancelled(true);
        this.rememberLid(player.getUniqueId(), inventory.getLocation());
        this.enderchestInventory.openPage(player, chestOwner, EnderchestLayout.FIRST_PAGE);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player) || !this.enderchestInventory.isOpenPage(event.getInventory())) {
            return;
        }

        this.scheduler.run(player, () -> {
            if (this.enderchestInventory.isOpenPage(player.getOpenInventory().getTopInventory())) {
                return;
            }

            this.setLid(this.openLids.remove(player.getUniqueId()), false);
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onQuit(PlayerQuitEvent event) {
        this.setLid(this.openLids.remove(event.getPlayer().getUniqueId()), false);
    }

    private void rememberLid(UUID playerUniqueId, @Nullable Location location) {
        if (location == null) {
            this.openLids.remove(playerUniqueId);
            return;
        }

        this.openLids.put(playerUniqueId, location);
        this.setLid(location, true);
    }

    private void setLid(@Nullable Location location, boolean open) {
        if (location == null || location.getWorld() == null || !location.isChunkLoaded()) {
            return;
        }

        BlockState state = location.getBlock().getState();
        if (!(state instanceof EnderChest enderChest)) {
            return;
        }

        if (open) {
            enderChest.open();
            return;
        }

        enderChest.close();
    }
}
