package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.util.Vector;
import org.jspecify.annotations.NonNull;

/**
 * Controller allowing vanished players to silently access containers
 * without triggering animations/sounds for other players.
 * Temporarily switches player to Spectator mode for 1 tick.
 * <p>
 * Uses Caffeine cache for automatic cleanup and state management.
 * <p>
 * Implementation based on SayanVanish plugin approach.
 * Original idea: <a href="https://github.com/Syrent/SayanVanish/blob/master/sayanvanish-bukkit/src/main/kotlin/org/sayandev/sayanvanish/bukkit/feature/features/FeatureSilentContainer.kt">...</a>
 */
@Controller
class OpenSilentController implements Listener {

    private static final Vector ZERO_VELOCITY = new Vector(0.0, 0.0, 0.0);
    private static final int TICK_DURATION_MILLIS = 50;
    private static final int CACHE_EXPIRE_SECONDS = 5;

    private final NoticeService noticeService;
    private final VanishService vanishService;
    private final VanishSettings config;
    private final Scheduler scheduler;

    private final Cache<UUID, ContainerWrapper> containerCache;

    @Inject
    OpenSilentController(
        NoticeService noticeService,
        VanishService vanishService,
        VanishSettings config,
        Scheduler scheduler
    ) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
        this.config = config;
        this.scheduler = scheduler;

        this.containerCache = Caffeine.newBuilder()
            .expireAfterWrite(CACHE_EXPIRE_SECONDS, TimeUnit.SECONDS)
            .removalListener(new PlayerRestoreListener(scheduler))
            .build();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void handlePlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock() || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        if (!this.config.silentInventoryAccess()) {
            event.setCancelled(true);
            this.noticeService.player(
                player.getUniqueId(),
                message -> message.vanish().cantOpenInventoryWhileVanished()
            );
            return;
        }

        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        Material type = clickedBlock.getType();

        if (type == Material.ENDER_CHEST) {
            event.setCancelled(true);
            player.openInventory(player.getEnderChest());
            return;
        }

        if (!this.isContainerType(type) || !(clickedBlock.getState() instanceof Container)) {
            return;
        }

        this.switchForOneTick(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handlePlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        TeleportCause cause = event.getCause();

        if (cause == PlayerTeleportEvent.TeleportCause.SPECTATE) {
            return;
        }

        if (this.containerCache.getIfPresent(player.getUniqueId()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        ContainerWrapper data = this.containerCache.getIfPresent(playerId);
        if (data != null) {
            this.containerCache.invalidate(playerId);
            data.apply(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void handleInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        if (event.getInventory() instanceof CraftingInventory) {
            return;
        }

        if (this.containerCache.getIfPresent(player.getUniqueId()) == null) {
            this.switchForOneTick(player);
        }
    }

    private void switchForOneTick(Player player) {
        UUID playerId = player.getUniqueId();

        if (this.containerCache.getIfPresent(playerId) != null) {
            return;
        }

        ContainerWrapper playerData = new ContainerWrapper(
            player.getGameMode(),
            player.getAllowFlight(),
            player.isFlying()
        );
        this.containerCache.put(playerId, playerData);

        player.setAllowFlight(true);
        player.setFlying(true);
        player.setVelocity(ZERO_VELOCITY);
        player.setGameMode(GameMode.SPECTATOR);
        this.scheduler.runLater(() -> this.restorePlayer(playerId), Duration.ofMillis(TICK_DURATION_MILLIS));
    }

    private void restorePlayer(UUID playerId) {
        ContainerWrapper data = this.containerCache.getIfPresent(playerId);
        if (data != null) {
            this.containerCache.invalidate(playerId);

            Player player = Bukkit.getPlayer(playerId);
            if (player != null && player.isOnline()) {
                data.apply(player);
            }
        }
    }

    private boolean isContainerType(Material type) {
        return switch (type) {
            case CHEST, TRAPPED_CHEST, BARREL, ENDER_CHEST -> true;
            default -> Tag.SHULKER_BOXES.isTagged(type);
        };
    }

    private record PlayerRestoreListener(Scheduler scheduler) implements RemovalListener<UUID, ContainerWrapper> {

        @Override
        public void onRemoval(UUID playerId, ContainerWrapper data, @NonNull RemovalCause cause) {
            if (cause == RemovalCause.EXPIRED && data != null) {

                this.scheduler.run(() -> {
                    Player player = Bukkit.getPlayer(playerId);
                    if (player != null && player.isOnline()) {
                        data.apply(player);
                    }
                });
            }
        }
    }

    private record ContainerWrapper(GameMode gameMode, boolean allowFlight, boolean isFlying) {
        public void apply(Player player) {
            if (player.isOnline()) {
                player.setGameMode(gameMode);
                player.setAllowFlight(allowFlight);
                player.setFlying(isFlying);
            }
        }
    }
}
