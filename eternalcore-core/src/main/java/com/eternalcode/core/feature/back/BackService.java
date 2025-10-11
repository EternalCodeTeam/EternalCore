package com.eternalcode.core.feature.back;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
public class BackService {

    private static final String BYPASS_PERMISSION = "eternalcore.teleport.bypass";

    private final TeleportService teleportService;
    private final TeleportTaskService teleportTaskService;
    private final TeleportRequestSettings settings;

    private final Cache<UUID, Position> deathLocations;
    private final Cache<UUID, Position> teleportLocations;

    @Inject
    public BackService(
        TeleportService teleportService,
        TeleportTaskService teleportTaskService,
        TeleportRequestSettings settings
    ) {
        this.teleportService = teleportService;
        this.teleportTaskService = teleportTaskService;
        this.settings = settings;

        this.deathLocations = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();
        this.teleportLocations = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();
    }

    public Optional<Position> getDeathLocation(UUID playerId) {
        return Optional.ofNullable(deathLocations.getIfPresent(playerId));
    }

    public Optional<Position> getTeleportLocation(UUID playerId) {
        return Optional.ofNullable(teleportLocations.getIfPresent(playerId));
    }

    public void markDeathLocation(UUID player, Position position) {
        this.deathLocations.put(player, position);
    }

    public void markTeleportLocation(UUID player, Position position) {
        this.teleportLocations.put(player, position);
    }

    public void teleportBack(Player player, Location location) {
        if (player.hasPermission(BYPASS_PERMISSION)) {
            teleportService.teleport(player, location);
        } else {
            teleportTaskService.createTeleport(
                player.getUniqueId(),
                PositionAdapter.convert(player.getLocation()),
                PositionAdapter.convert(location),
                settings.tpaTimer()
            );
        }
    }
}
