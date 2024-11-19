package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.randomteleport.event.PreRandomTeleportEvent;
import com.eternalcode.core.feature.randomteleport.event.RandomTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import io.papermc.lib.PaperLib;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

@Service
class RandomTeleportServiceImpl implements RandomTeleportService {

    private static final int DEFAULT_NETHER_HEIGHT = 125;
    private static final int NETHER_MAX_HEIGHT = 127;

    private final RandomTeleportSettings randomTeleportSettings;
    private final LocationsConfiguration locationsConfiguration;
    private final EventCaller eventCaller;
    private final Server server;
    private final Random random = new Random();

    @Inject
    RandomTeleportServiceImpl(
        RandomTeleportSettings randomTeleportSettings,
        LocationsConfiguration locationsConfiguration,
        EventCaller eventCaller,
        Server server
    ) {
        this.randomTeleportSettings = randomTeleportSettings;
        this.locationsConfiguration = locationsConfiguration;
        this.eventCaller = eventCaller;
        this.server = server;
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
        PreRandomTeleportEvent preRandomTeleportEvent = this.eventCaller.callEvent(new PreRandomTeleportEvent(player));

        if (preRandomTeleportEvent.isCancelled()) {
            return CompletableFuture.completedFuture(new TeleportResult(false, player.getLocation()));
        }

        return this.getSafeRandomLocation(world, this.randomTeleportSettings.randomTeleportAttempts())
            .thenCompose(location -> PaperLib.teleportAsync(player, location).thenApply(success -> {
                TeleportResult teleportResult = new TeleportResult(success, location);

                RandomTeleportEvent event = new RandomTeleportEvent(player, location);
                this.eventCaller.callEvent(event);

                return teleportResult;
            }));
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount) {
        RandomTeleportType type = this.randomTeleportSettings.randomTeleportType();
        RandomTeleportRadiusRepresenter radius = this.randomTeleportSettings.randomTeleportRadius();

        return this.getSafeRandomLocation(world, type, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int radius, int attemptCount) {
        return this.getSafeRandomLocation(world, RandomTeleportType.STATIC_RADIUS, RandomTeleportRadiusRepresenterImpl.of(radius), attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(
        World world,
        RandomTeleportRadiusRepresenter radius,
        int attemptCount) {
        return this.getSafeRandomLocation(world, RandomTeleportType.STATIC_RADIUS, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount) {
        return this.getSafeRandomLocation(world, RandomTeleportType.WORLD_BORDER_RADIUS, null, attemptCount);
    }

    private CompletableFuture<Location> getSafeRandomLocation(
        World world,
        RandomTeleportType type,
        RandomTeleportRadiusRepresenter radius,
        int attemptCount
    ) {
        if (attemptCount < 0) {
            return CompletableFuture.failedFuture(new RuntimeException("Cannot find safe location"));
        }

        if (type == RandomTeleportType.WORLD_BORDER_RADIUS) {
            WorldBorder worldBorder = world.getWorldBorder();
            int borderRadius = (int) (worldBorder.getSize() / 2);
            radius = RandomTeleportRadiusRepresenterImpl.of(-borderRadius, borderRadius, -borderRadius, borderRadius);
        }

        boolean noneWorld = this.locationsConfiguration.spawn.isNoneWorld();
        Location spawnLocation = !noneWorld
            ? PositionAdapter.convert(this.locationsConfiguration.spawn)
            : world.getSpawnLocation();

        int spawnX = spawnLocation.getBlockX();
        int spawnZ = spawnLocation.getBlockZ();

        int randomX = spawnX + (int) (this.random.nextDouble() * (radius.getMaxX() - radius.getMinX()) + radius.getMinX());
        int randomZ = spawnZ + (int) (this.random.nextDouble() * (radius.getMaxZ() - radius.getMinZ()) + radius.getMinZ());

        RandomTeleportRadiusRepresenter finalRadius = radius;
        return PaperLib.getChunkAtAsync(new Location(world, randomX, 100, randomZ)).thenCompose(chunk -> {
            int randomY = chunk.getWorld().getHighestBlockYAt(randomX, randomZ);

            if (world.getEnvironment() == World.Environment.NETHER) {
                randomY = this.random.nextInt(DEFAULT_NETHER_HEIGHT);
            }

            int minHeight = this.randomTeleportSettings.minHeight();
            int maxHeight = world.getMaxHeight() - 1;
            if (randomY < minHeight) {
                randomY = minHeight;
            }

            if (randomY > maxHeight) {
                randomY = maxHeight;
            }

            Location generatedLocation = new Location(world, randomX, randomY, randomZ).add(0.5, 1, 0.5);

            if (this.isSafeLocation(chunk, generatedLocation)) {
                return CompletableFuture.completedFuture(generatedLocation);
            }

            return this.getSafeRandomLocation(world, type, finalRadius, attemptCount - 1);
        });
    }

    private boolean isSafeLocation(Chunk chunk, Location location) {
        if (location == null || location.getWorld() == null) {
            return false;
        }

        World world = chunk.getWorld();
        Block block = world.getBlockAt(location);
        Block blockAbove = block.getRelative(BlockFace.UP);
        Block blockFloor = block.getRelative(BlockFace.DOWN);

        if (this.randomTeleportSettings.unsafeBlocks().contains(blockFloor.getType())) {
            return false;
        }

        Set<Material> airBlocks = this.randomTeleportSettings.airBlocks();
        if (!airBlocks.contains(block.getType()) || !airBlocks.contains(blockAbove.getType())) {
            return false;
        }

        if (!blockFloor.getType().isSolid()) {
            return false;
        }

        WorldBorder worldBorder = world.getWorldBorder();

        if (!worldBorder.isInside(location)) {
            return false;
        }

        return switch (world.getEnvironment()) {
            case NORMAL, THE_END -> true;
            case NETHER -> location.getY() <= NETHER_MAX_HEIGHT;
            default -> false;
        };
    }
}
