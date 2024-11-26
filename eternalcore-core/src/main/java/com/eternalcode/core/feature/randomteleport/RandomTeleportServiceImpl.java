package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

@Service
class RandomTeleportServiceImpl implements RandomTeleportService {

    private final RandomTeleportSettings randomTeleportSettings;
    private final Server server;
    private final RandomTeleportSafeLocationService safeLocationService;
    private final RandomTeleportTaskService randomTeleportTaskService;

    @Inject
    RandomTeleportServiceImpl(
        RandomTeleportSettings randomTeleportSettings,
        Server server,
        RandomTeleportSafeLocationService safeLocationService,
        RandomTeleportTaskService randomTeleportTaskService
    ) {
        this.randomTeleportSettings = randomTeleportSettings;
        this.server = server;
        this.safeLocationService = safeLocationService;
        this.randomTeleportTaskService = randomTeleportTaskService;
    }

    @Override
    public CompletableFuture<RandomTeleportResult> teleport(Player player) {
        World world = player.getWorld();

        if (!this.randomTeleportSettings.randomTeleportWorld().isBlank()) {
            world = this.server.getWorld(this.randomTeleportSettings.randomTeleportWorld());

            if (world == null) {
                throw new IllegalStateException(
                    "World " + this.randomTeleportSettings.randomTeleportWorld() + " does not exist!");
            }
        }

        return this.teleport(player, world);
    }

    @Override
    public CompletableFuture<RandomTeleportResult> teleport(Player player, World world) {
        return this.getSafeRandomLocation(world, this.randomTeleportSettings.randomTeleportAttempts())
            .thenCompose(location -> this.randomTeleportTaskService.createTeleport(player, location));
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount) {
        RandomTeleportRadius radius = switch (this.randomTeleportSettings.randomTeleportType()) {
            case STATIC_RADIUS -> this.randomTeleportSettings.randomTeleportRadius();
            case WORLD_BORDER_RADIUS -> this.getWorldBorderRadius(world);
        };

        return this.safeLocationService.getSafeRandomLocation(world, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int radius, int attemptCount) {
        return this.safeLocationService.getSafeRandomLocation(world, RandomTeleportRadius.of(radius), attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, RandomTeleportRadius radius, int attemptCount) {
        return this.safeLocationService.getSafeRandomLocation(world, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount) {
        return this.safeLocationService.getSafeRandomLocation(world, this.getWorldBorderRadius(world), attemptCount);
    }

    private RandomTeleportRadius getWorldBorderRadius(World world) {
        WorldBorder worldBorder = world.getWorldBorder();
        int borderRadius = (int) (worldBorder.getSize() / 2);

        return RandomTeleportRadius.of(-borderRadius, borderRadius, -borderRadius, borderRadius);
    }

}
