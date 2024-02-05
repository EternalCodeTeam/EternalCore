package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.randomteleport.event.PreRandomTeleportEvent;
import com.eternalcode.core.feature.randomteleport.event.RandomTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.shared.PositionAdapter;
import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
class RandomTeleportServiceImpl implements RandomTeleportService {

    private static final Set<Material> UNSAFE_BLOCKS = EnumSet.of(
        Material.LAVA,
        Material.WATER,
        Material.CACTUS,
        Material.FIRE,
        Material.COBWEB,
        Material.SWEET_BERRY_BUSH,
        Material.MAGMA_BLOCK
    );

    private static final Set<Material> AIR_BLOCKS = EnumSet.of(
        Material.AIR,
        Material.CAVE_AIR,
        Material.VOID_AIR,
        Material.GRASS,
        Material.TALL_GRASS,
        Material.VINE
    );

    private static final int DEFAULT_NETHER_HEIGHT = 125;
    private static final int NETHER_MAX_HEIGHT = 127;

    private final RandomTeleportSettings randomTeleportSettings;
    private final LocationsConfiguration locationsConfiguration;
    private final EventCaller eventCaller;
    private final Server server;
    private final Random random = new Random();

    @Inject
    RandomTeleportServiceImpl(RandomTeleportSettings randomTeleportSettings, LocationsConfiguration locationsConfiguration, EventCaller eventCaller, Server server) {
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
                throw new IllegalStateException("World " + this.randomTeleportSettings.randomTeleportWorld() + " is not exists!");
            }
        }


        return this.teleport(player, world);
    }

    @Override
    public CompletableFuture<TeleportResult> teleport(Player player, World world) {
        PreRandomTeleportEvent preRandomTeleportEvent = new PreRandomTeleportEvent(player);

        this.eventCaller.callEvent(preRandomTeleportEvent);

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
        int radius = this.randomTeleportSettings.randomTeleportRadius();

        return this.getSafeRandomLocation(world, type, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, RandomTeleportType type, int radius, int attemptCount) {
        if (attemptCount < 0) {
            return CompletableFuture.failedFuture(new RuntimeException("Cannot find safe location"));
        }

        if (type == RandomTeleportType.WORLD_BORDER_RADIUS) {
            WorldBorder worldBorder = world.getWorldBorder();
            radius = (int) worldBorder.getSize() / 2;
        }

        boolean noneWorld = this.locationsConfiguration.spawn.isNoneWorld();
        Location spawnLocation = !noneWorld
            ? PositionAdapter.convert(this.locationsConfiguration.spawn)
            : world.getSpawnLocation();

        int spawnX = spawnLocation.getBlockX();
        int spawnZ = spawnLocation.getBlockZ();

        int randomX = spawnX + this.random.nextInt(-radius, radius);
        int randomZ = spawnZ + this.random.nextInt(-radius, radius);

        return PaperLib.getChunkAtAsync(new Location(world, randomX, 100, randomZ)).thenCompose(chunk -> {
            int randomY = chunk.getWorld().getHighestBlockYAt(randomX, randomZ);

            if (world.getEnvironment() == World.Environment.NETHER) {
                randomY = this.random.nextInt(DEFAULT_NETHER_HEIGHT);
            }

            Location generatedLocation = new Location(world, randomX, randomY, randomZ).add(0.5, 1, 0.5);

            if (this.isSafeLocation(chunk, generatedLocation)) {
                return CompletableFuture.completedFuture(generatedLocation);
            }

            return this.getSafeRandomLocation(world, attemptCount - 1);
        });
    }

    public boolean isSafeLocation(Chunk chunk, Location location) {
        if (location == null || location.getWorld() == null) {
            return false;
        }

        World world = chunk.getWorld();
        Block block = world.getBlockAt(location);
        Block blockAbove = block.getRelative(BlockFace.UP);
        Block blockFloor = block.getRelative(BlockFace.DOWN);

        if (UNSAFE_BLOCKS.contains(blockFloor.getType())) {
            return false;
        }

        if (!AIR_BLOCKS.contains(block.getType()) || !AIR_BLOCKS.contains(blockAbove.getType())) {
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
