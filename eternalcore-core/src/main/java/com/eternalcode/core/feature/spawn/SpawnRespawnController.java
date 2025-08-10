package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

@Controller
class SpawnRespawnController implements Listener {

    private final SpawnJoinSettings spawnJoinSettings;
    private final TeleportService teleportService;
    private final SpawnService spawnService;

    @Inject
    SpawnRespawnController(
        SpawnJoinSettings spawnJoinSettings,
        TeleportService teleportService,
        SpawnService spawnService
    ) {
        this.spawnJoinSettings = spawnJoinSettings;
        this.teleportService = teleportService;
        this.spawnService = spawnService;
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        boolean forceSpawn = this.spawnJoinSettings.alwaysTeleportToSpawnAfterDeath();
        boolean usePersonalRespawn = this.spawnJoinSettings.teleportToPersonalRespawnPoint() && this.hasRespawnPoint(player);

        if (usePersonalRespawn && !forceSpawn) {
            return;
        }

        if (this.spawnJoinSettings.teleportToSpawnAfterDeath()) {
            Location spawnLocation = this.spawnService.getSpawnLocation();
            if (spawnLocation != null) {
                this.teleportService.teleport(player, spawnLocation);
            }
        }
    }

    private boolean hasRespawnPoint(Player player) {
        return player.getRespawnLocation() != null;
    }
}
