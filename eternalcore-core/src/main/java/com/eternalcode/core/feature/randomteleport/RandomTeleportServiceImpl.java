package com.eternalcode.core.feature.randomteleport;

import static com.eternalcode.core.feature.randomteleport.RandomTeleportResolveWorldUtil.resolveWorld;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.randomteleport.event.PreRandomTeleportEvent;
import com.eternalcode.core.feature.randomteleport.event.RandomTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import io.papermc.lib.PaperLib;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

@Service
class RandomTeleportServiceImpl implements RandomTeleportService {

    private final RandomTeleportSettings randomTeleportSettings;
    private final RandomTeleportSafeLocationService safeLocationService;
    private final EventCaller eventCaller;

    @Inject
    RandomTeleportServiceImpl(
        RandomTeleportSettings randomTeleportSettings,
        RandomTeleportSafeLocationService safeLocationService, EventCaller eventCaller
    ) {
        this.randomTeleportSettings = randomTeleportSettings;
        this.safeLocationService = safeLocationService;
        this.eventCaller = eventCaller;
    }

    @Override
    public CompletableFuture<RandomTeleportResult> teleport(Player player) {
        World world = resolveWorld(player, randomTeleportSettings);
        return this.teleport(player, world);
    }

    @Override
    public CompletableFuture<RandomTeleportResult> teleport(Player player, World world) {
        PreRandomTeleportEvent preRandomTeleportEvent = this.eventCaller.callEvent(new PreRandomTeleportEvent(player));

        if (preRandomTeleportEvent.isCancelled()) {
            return CompletableFuture.completedFuture(new RandomTeleportResult(false, player.getLocation()));
        }

        return this.getSafeRandomLocation(world, this.randomTeleportSettings.teleportAttempts())
            .thenCompose(location -> PaperLib.teleportAsync(player, location).thenApply(success -> {
                RandomTeleportResult teleportResult = new RandomTeleportResult(success, location);

                RandomTeleportEvent event = new RandomTeleportEvent(player, location);
                this.eventCaller.callEvent(event);

                return teleportResult;
            }));
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount) {
        RandomTeleportRadius radius = switch (this.randomTeleportSettings.radiusType()) {
            case STATIC_RADIUS -> this.randomTeleportSettings.radius();
            case WORLD_BORDER_RADIUS -> this.getWorldBorderRadius(world);
        };

        return this.safeLocationService.getSafeRandomLocation(world, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int radius, int attemptCount) {
        return this.safeLocationService.getSafeRandomLocation(world, RandomTeleportRadius.of(radius), attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(
        World world,
        RandomTeleportRadius radius,
        int attemptCount
    ) {
        return this.safeLocationService.getSafeRandomLocation(world, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount) {
        return this.getSafeRandomLocation(world, this.getWorldBorderRadius(world), attemptCount);
    }

    private RandomTeleportRadius getWorldBorderRadius(World world) {
        WorldBorder worldBorder = world.getWorldBorder();
        int borderRadius = (int) (worldBorder.getSize() / 2);
        return RandomTeleportRadius.of(-borderRadius, borderRadius, -borderRadius, borderRadius);
    }
}
