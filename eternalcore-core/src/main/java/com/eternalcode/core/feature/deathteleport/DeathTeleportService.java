package com.eternalcode.core.feature.deathteleport;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.UUID;

@Service
class DeathTeleportService {

    private final Cache<UUID, Position> deathLocations;

    @Inject
    DeathTeleportService(
        DeathTeleportSettings settings
    ) {
        this.deathLocations = Caffeine.newBuilder()
            .expireAfterWrite(settings.deathLocationCacheDuration())
            .build();
    }

    void markDeathLocation(
        UUID playerId, Position position
    ) {
        this.deathLocations.put(playerId, position);
    }

    Optional<Position> getDeathLocation(
        UUID playerId
    ) {
        return Optional.ofNullable(this.deathLocations.getIfPresent(playerId));
    }

    void clearDeathLocation(
        UUID playerId
    ) {
        this.deathLocations.invalidate(playerId);
    }
}
