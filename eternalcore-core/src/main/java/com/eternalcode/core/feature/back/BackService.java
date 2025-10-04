package com.eternalcode.core.feature.back;

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
import panda.std.Pair;

@Service
public class BackService {

    private final TeleportService teleportService;
    private final TeleportTaskService teleportTaskService;
    private final TeleportRequestSettings settings;

    private final Cache<UUID, Pair<BackLocation, BackLocation>> backLocationsCache;

    @Inject
    public BackService(
        TeleportService teleportService,
        TeleportTaskService teleportTaskService,
        TeleportRequestSettings settings
    ) {
        this.teleportService = teleportService;
        this.teleportTaskService = teleportTaskService;
        this.settings = settings;

        this.backLocationsCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();
    }

    public Optional<Pair<BackLocation, BackLocation>> getBackLocationPair(UUID playerId) {
        return Optional.ofNullable(backLocationsCache.getIfPresent(playerId));
    }

    public void setBackLocation(UUID playerId, Location location, boolean isFromDeath) {
        Pair<BackLocation, BackLocation> existing = backLocationsCache.getIfPresent(playerId);
        BackLocation newLocation = new BackLocation(location);
        BackLocation deathLocation;
        BackLocation normalLocation;
        if (existing == null) {
            deathLocation = isFromDeath ? newLocation : null;
            normalLocation = isFromDeath ? null : newLocation;
        }
        else {
            deathLocation = isFromDeath ? newLocation : existing.getFirst();
            normalLocation = isFromDeath ? existing.getSecond() : newLocation;
        }
        backLocationsCache.put(playerId, Pair.of(deathLocation, normalLocation));
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

    public record BackLocation(Location location) {
    }
}
