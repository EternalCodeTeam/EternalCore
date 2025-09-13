package com.eternalcode.core.feature.back;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BackService {

    private final TeleportService teleportService;
    private final TeleportTaskService teleportTaskService;
    private final TeleportRequestSettings settings;

    private final Map<UUID, BackLocation> backLocations = new HashMap<>();

    @Inject
    public BackService(
        TeleportService teleportService,
        TeleportTaskService teleportTaskService,
        TeleportRequestSettings settings
    ) {
        this.teleportService = teleportService;
        this.teleportTaskService = teleportTaskService;
        this.settings = settings;
    }

    public Optional<BackLocation> getBackLocation(UUID playerId) {
        return Optional.ofNullable(backLocations.get(playerId));
    }

    public void setBackLocation(UUID playerId, Location location, boolean isFromDeath) {
        backLocations.put(playerId, new BackLocation(location, isFromDeath));
    }

    public void teleportBack(Player player, Location location) {
        if (player.hasPermission("eternalcore.teleport.bypass")) {
            teleportService.teleport(player, location);
        }
        else {
            teleportTaskService.createTeleport(
                player.getUniqueId(),
                PositionAdapter.convert(player.getLocation()),
                PositionAdapter.convert(location),
                settings.tpaTimer()
            );
        }
    }

    public record BackLocation(Location location, boolean isFromDeath) {
    }
}
