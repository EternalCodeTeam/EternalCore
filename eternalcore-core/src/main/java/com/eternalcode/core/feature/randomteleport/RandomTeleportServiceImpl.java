package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
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
    public CompletableFuture<TeleportResult> teleport(Player player) {
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
    public CompletableFuture<TeleportResult> teleport(Player player, World world) {
        this.getSafeRandomLocation(world, this.randomTeleportSettings.randomTeleportAttempts())
            .thenCompose(location -> this.randomTeleportTaskService.createTeleport(player, location));

        return CompletableFuture.completedFuture(new TeleportResult(true, player.getLocation()));
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount) {
        RandomTeleportType type = this.randomTeleportSettings.randomTeleportType();
        RandomTeleportRadiusRepresenter radius = this.randomTeleportSettings.randomTeleportRadius();
        return this.safeLocationService.getSafeRandomLocation(world, type, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int radius, int attemptCount) {
        return this.safeLocationService.getSafeRandomLocation(
            world,
            RandomTeleportType.STATIC_RADIUS,
            RandomTeleportRadiusRepresenterImpl.of(radius),
            attemptCount
        );
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(
        World world,
        RandomTeleportRadiusRepresenter radius,
        int attemptCount
    ) {
        return this.safeLocationService.getSafeRandomLocation(
            world,
            RandomTeleportType.STATIC_RADIUS,
            radius,
            attemptCount
        );
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount) {
        return this.safeLocationService.getSafeRandomLocation(
            world,
            RandomTeleportType.WORLD_BORDER_RADIUS,
            null,
            attemptCount
        );
    }
}
