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
    private final RandomTeleportServiceImpl randomTeleportService;
    private final Random random = new Random();

    @Inject
    RandomTeleportSafeLocationService(
        RandomTeleportSettings randomTeleportSettings,
        LocationsConfiguration locationsConfiguration,
        RandomTeleportServiceImpl randomTeleportService
    ) {
        this.randomTeleportSettings = randomTeleportSettings;
        this.locationsConfiguration = locationsConfiguration;
        this.randomTeleportService = randomTeleportService;
    }

    public CompletableFuture<Location> getSafeRandomLocation(World world, RandomTeleportRadius radius, int attemptCount) {
        if (attemptCount < 0) {
            return CompletableFuture.failedFuture(new RuntimeException("Cannot find safe location"));
        }

        boolean noneWorld = this.locationsConfiguration.spawn.isNoneWorld();
        Location spawnLocation = !noneWorld
            ? PositionAdapter.convert(this.locationsConfiguration.spawn)
            : world.getSpawnLocation();

        int spawnX = spawnLocation.getBlockX();
        int spawnZ = spawnLocation.getBlockZ();

        int randomX = spawnX + this.random.nextInt(radius.maxX() - radius.minX() + 1) + radius.minX();
        int randomZ = spawnZ + this.random.nextInt(radius.maxZ() - radius.minZ() + 1) + radius.minZ();

        return PaperLib.getChunkAtAsync(new Location(world, randomX, 100, randomZ)).thenCompose(chunk -> {
            int randomY = chunk.getWorld().getHighestBlockYAt(randomX, randomZ);

            if (world.getEnvironment() == World.Environment.NETHER) {
                randomY = this.random.nextInt(DEFAULT_NETHER_HEIGHT);
            }

            RandomTeleportHeightRange heightRange = this.randomTeleportSettings.heightRange();
            int minHeight = heightRange.getMinY();
            int maxHeight = heightRange.getMaxY() - 1;
            randomY = Math.min(Math.max(randomY, minHeight), maxHeight);

            Location generatedLocation = new Location(world, randomX, randomY, randomZ).add(0.5, 1, 0.5);

            if (this.isSafeLocation(chunk, generatedLocation)) {
                return CompletableFuture.completedFuture(generatedLocation);
            }

            return this.getSafeRandomLocation(world, radius, attemptCount - 1);
        });
    }

    private boolean isSafeLocation(Chunk chunk, Location location) {
        if (location.getWorld() == null) {
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

        boolean environmentValid = switch (world.getEnvironment()) {
            case NORMAL, THE_END, CUSTOM -> true;
            case NETHER -> location.getY() <= NETHER_MAX_HEIGHT;
        };

        if (!environmentValid) {
            return false;
        }

        for (RandomTeleportLocationFilter filter : this.randomTeleportService.getRegisteredFiltersInternal()) {
            if (!filter.isValid(location)) {
                return false;
            }
        }

        return true;
    }
}
