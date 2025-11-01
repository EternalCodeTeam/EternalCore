package com.eternalcode.core.feature.back;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
class BackService {

    private static final String BYPASS_PERMISSION = "eternalcore.teleport.bypass";

    private final TeleportService teleportService;
    private final TeleportTaskService teleportTaskService;
    private final BackSettings settings;

    private final Cache<UUID, Position> deathLocations;
    private final Cache<UUID, Position> teleportLocations;
    private final Cache<UUID, Position> latestLocations;

    @Inject
    BackService(
        TeleportService teleportService,
        TeleportTaskService teleportTaskService,
        BackSettings settings
    ) {
        this.teleportService = teleportService;
        this.teleportTaskService = teleportTaskService;
        this.settings = settings;

        this.deathLocations = Caffeine.newBuilder()
            .expireAfterWrite(settings.backLocationCacheDuration())
            .build();
        this.teleportLocations = Caffeine.newBuilder()
            .expireAfterWrite(settings.backLocationCacheDuration())
            .build();
        this.latestLocations = Caffeine.newBuilder()
            .expireAfterWrite(settings.backLocationCacheDuration())
            .build();
    }

    public Optional<Position> getDeathLocation(UUID playerId) {
        return Optional.ofNullable(deathLocations.getIfPresent(playerId));
    }

    public Optional<Position> getTeleportLocation(UUID playerId) {
        return Optional.ofNullable(teleportLocations.getIfPresent(playerId));
    }

    public Optional<Position> getLatestLocation(UUID playerId) {
        return Optional.ofNullable(latestLocations.getIfPresent(playerId));
    }

    public void markDeathLocation(UUID player, Position position) {
        this.deathLocations.put(player, position);
        this.latestLocations.put(player, position);
    }

    public void markTeleportLocation(UUID player, Position position) {
        this.teleportLocations.put(player, position);
        this.latestLocations.put(player, position);
    }

    public boolean teleportBack(Player target) {
        Optional<Position> teleportLocation = this.getTeleportLocation(target.getUniqueId());

        if (teleportLocation.isPresent()) {
            this.teleportBack(target, PositionAdapter.convert(teleportLocation.get()));

            return true;
        }
        return false;
    }

    public boolean teleportBackDeath(Player target) {
        Optional<Position> deathLocation = this.getDeathLocation(target.getUniqueId());

        if (deathLocation.isPresent()) {
            this.teleportBack(target, PositionAdapter.convert(deathLocation.get()));

            return true;
        }
        return false;
    }

    private void teleportBack(Player player, Location location) {
        if (player.hasPermission(BYPASS_PERMISSION)) {
            teleportService.teleport(player, location);
            return;
        }
        teleportTaskService.createTeleport(
            player.getUniqueId(),
            PositionAdapter.convert(player.getLocation()),
            PositionAdapter.convert(location),
            settings.backTeleportTimer()
        );
    }
}
