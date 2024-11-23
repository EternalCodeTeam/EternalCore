package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
class RandomTeleportSafeLocationService {

    private static final int DEFAULT_NETHER_HEIGHT = 125;
    private static final int NETHER_MAX_HEIGHT = 127;

    private final RandomTeleportSettings randomTeleportSettings;
    private final LocationsConfiguration locationsConfiguration;
    private final Random random = new Random();

    @Inject
    RandomTeleportSafeLocationService(
        RandomTeleportSettings randomTeleportSettings,
        LocationsConfiguration locationsConfiguration
    ) {
        this.randomTeleportSettings = randomTeleportSettings;
        this.locationsConfiguration = locationsConfiguration;
    }

    public CompletableFuture<Location> getSafeRandomLocation(World world, RandomTeleportType type,
        RandomTeleportRadiusRepresenter radius,
        int attemptCount) {
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

        int randomX = spawnX + this.random.nextInt(radius.getMaxX() - radius.getMinX() + 1) + radius.getMinX();
        int randomZ = spawnZ + this.random.nextInt(radius.getMaxZ() - radius.getMinZ() + 1) + radius.getMinZ();

        RandomTeleportRadiusRepresenter finalRadius = radius;
        return PaperLib.getChunkAtAsync(new Location(world, randomX, 100, randomZ)).thenCompose(chunk -> {
            int randomY = chunk.getWorld().getHighestBlockYAt(randomX, randomZ);

            if (world.getEnvironment() == World.Environment.NETHER) {
                randomY = this.random.nextInt(DEFAULT_NETHER_HEIGHT);
            }

            RandomTeleportHeightRangeRepresenter heightRange = this.randomTeleportSettings.heightRange();
            int minHeight = heightRange.getMinY();
            int maxHeight = heightRange.getMaxY() - 1;
            randomY = Math.min(Math.max(randomY, minHeight), maxHeight);

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
