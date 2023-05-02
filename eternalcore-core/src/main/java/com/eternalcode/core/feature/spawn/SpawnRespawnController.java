package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportService;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnRespawnController implements Listener {

    private final TeleportService teleportService;
    private final PluginConfiguration config;
    private final LocationsConfiguration locations;

    public SpawnRespawnController(TeleportService teleportService, PluginConfiguration config, LocationsConfiguration locations) {
        this.teleportService = teleportService;
        this.config = config;
        this.locations = locations;
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (this.config.teleport.teleportToSpawnOnDeath) {
            Location destinationLocation = PositionAdapter.convert(this.locations.spawn);
            this.teleportService.teleport(player, destinationLocation);
        }
    }

}
