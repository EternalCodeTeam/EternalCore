package com.eternalcode.core.feature.enderchest;

import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.enderchest.database.EnderchestRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Service
class VanillaEnderchestMigration {

    private final Set<UUID> runningImports = ConcurrentHashMap.newKeySet();

    private final EnderchestManager enderchestManager;
    private final EnderchestRepository repository;
    private final EnderchestSettings settings;
    private final MinecraftScheduler scheduler;
    private final Server server;
    private final Logger logger;

    @Inject
    VanillaEnderchestMigration(
        EnderchestManager enderchestManager,
        EnderchestRepository repository,
        EnderchestSettings settings,
        MinecraftScheduler scheduler,
        Server server,
        Logger logger
    ) {
        this.enderchestManager = enderchestManager;
        this.repository = repository;
        this.settings = settings;
        this.scheduler = scheduler;
        this.server = server;
        this.logger = logger;
    }

    void migrateOnlinePlayers() {
        for (Player player : this.server.getOnlinePlayers()) {
            this.migratePlayer(player);
        }
    }

    void migratePlayer(Player player) {
        if (this.settings.enderchestsBlocked() || !this.settings.replaceVanillaEnderchest()) {
            return;
        }

        this.enderchestManager.loadEnderchest(player.getUniqueId(), player.getName())
            .thenAccept(enderchest -> this.scheduler.run(player, () -> this.importVanillaItems(player, enderchest)))
            .exceptionally(FutureHandler::handleException);
    }

    private void importVanillaItems(Player player, Enderchest enderchest) {
        UUID ownerUniqueId = enderchest.getOwnerUniqueId();

        if (!player.isOnline() || !this.enderchestManager.isLoaded(ownerUniqueId, enderchest)) {
            return;
        }

        if (enderchest.hasViewers() || enderchest.isWriting() || this.runningImports.contains(ownerUniqueId)) {
            return;
        }

        Inventory vanillaEnderchest = player.getEnderChest();
        ItemStack[] vanillaItems = vanillaEnderchest.getContents();

        if (enderchest.hasAlreadyImported(vanillaItems)) {
            if (enderchest.hasNothingToWrite()) {
                vanillaEnderchest.clear();
            }

            this.enderchestManager.unloadUnviewedEnderchest(ownerUniqueId);
            return;
        }

        int[] filledSlots = enderchest.insertItems(vanillaItems);
        if (filledSlots.length == 0) {
            this.enderchestManager.unloadUnviewedEnderchest(ownerUniqueId);
            return;
        }

        EnderchestWrite write = enderchest.prepareWrite();
        if (write.isEmpty()) {
            this.enderchestManager.unloadUnviewedEnderchest(ownerUniqueId);
            return;
        }

        this.runningImports.add(ownerUniqueId);

        this.repository.savePages(ownerUniqueId, write)
            .whenComplete((unused, throwable) -> this.scheduler.run(() -> {
                this.applyImportResult(player, enderchest, write, filledSlots, throwable);
                this.runningImports.remove(ownerUniqueId);
                enderchest.finishWrite();
                this.enderchestManager.unloadUnviewedEnderchest(ownerUniqueId);
            }));
    }

    private void applyImportResult(
        Player player,
        Enderchest enderchest,
        EnderchestWrite write,
        int[] filledSlots,
        Throwable throwable
    ) {
        if (throwable != null) {
            enderchest.restoreDirtyPages(write);
            enderchest.clearSlots(filledSlots);
            this.logger.log(Level.SEVERE, "Failed to migrate the vanilla ender chest of " + enderchest.getOwnerName()
                + ", its items stay in the vanilla one and the migration is retried on the next join or reload", throwable);
            return;
        }

        this.scheduler.run(player, () -> {
            if (player.isOnline()) {
                player.getEnderChest().clear();
            }
        });
    }
}
