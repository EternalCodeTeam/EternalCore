package com.eternalcode.core.feature.enderchest;

import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.event.EternalReloadEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldSaveEvent;

@Controller
class EnderchestSessionController implements Listener {

    private final EnderchestManager enderchestManager;
    private final EnderchestInventory enderchestInventory;
    private final VanillaEnderchestMigration migration;
    private final MinecraftScheduler scheduler;
    private final Server server;

    @Inject
    EnderchestSessionController(
        EnderchestManager enderchestManager,
        EnderchestInventory enderchestInventory,
        VanillaEnderchestMigration migration,
        MinecraftScheduler scheduler,
        Server server
    ) {
        this.enderchestManager = enderchestManager;
        this.enderchestInventory = enderchestInventory;
        this.migration = migration;
        this.scheduler = scheduler;
        this.server = server;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onJoin(PlayerJoinEvent event) {
        this.migration.migratePlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onQuit(PlayerQuitEvent event) {
        this.enderchestManager.unloadEnderchestOnQuit(event.getPlayer().getUniqueId());
    }

    @EventHandler
    void onWorldSave(WorldSaveEvent event) {
        if (event.getWorld().equals(this.server.getWorlds().getFirst())) {
            this.enderchestInventory.saveOpenPages();
        }
    }

    @Subscribe(EternalInitializeEvent.class)
    void onInitialize() {
        this.scheduler.run(this.migration::migrateOnlinePlayers);
    }

    @Subscribe(EternalReloadEvent.class)
    void onReload() {
        this.scheduler.run(() -> {
            this.enderchestInventory.closeAllPages();
            this.enderchestManager.unloadAllEnderchests();
            this.migration.migrateOnlinePlayers();
        });
    }

    @Subscribe(EternalShutdownEvent.class)
    void onShutdown() {
        this.enderchestInventory.closeAllPages();
        this.enderchestManager.shutdown();
    }
}
