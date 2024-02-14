package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportService;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;

@Controller
class SpawnRespawnController implements Listener {

    private final TeleportService teleportService;
    private final PluginConfiguration config;
    private final LocationsConfiguration locations;

    @Inject
    SpawnRespawnController(TeleportService teleportService, PluginConfiguration config, LocationsConfiguration locations) {
        this.teleportService = teleportService;
        this.config = config;
        this.locations = locations;
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (this.config.teleport.teleportToRespawnPoint && this.hasRespawnPoint(player)) {
            return;
        }

        Position spawnPosition = this.locations.spawn;

        if (this.config.teleport.teleportToSpawnOnDeath && !Objects.equals(spawnPosition.world(), Position.NONE_WORLD)) {
            Location destinationLocation = PositionAdapter.convert(spawnPosition);
            this.teleportService.teleport(player, destinationLocation);
        }
    }

    boolean hasRespawnPoint(Player player) {
        return player.getBedSpawnLocation() != null;
    }

}
