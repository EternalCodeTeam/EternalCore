package com.eternalcode.core.feature.rtp;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.scheduler.Scheduler;
import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RandomTeleportService {

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

    private static final int DEFAULT_WORLD_BORDER_SIZE = 29_999_984;

    private final Scheduler scheduler;

    private final PluginConfiguration.Teleport teleportConfiguration;

    private final Random random = new Random();

    public RandomTeleportService(PluginConfiguration.Teleport teleportConfiguration, Scheduler scheduler) {
        this.teleportConfiguration = teleportConfiguration;
        this.scheduler = scheduler;
    }

    public CompletableFuture<TeleportResult> teleport(Player player) {
        return this.getSafeRandomLocation(player.getWorld(), 100)
            .thenCompose(location -> PaperLib.teleportAsync(player, location).thenApply(success -> new TeleportResult(success, location)));
    }

    private CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount) {
        if (attemptCount < 0) {
            return CompletableFuture.failedFuture(new RuntimeException("Cannot find safe location"));
        }

        int radius = this.teleportConfiguration.randomTeleportRadius;

        if (this.hasSetWorldBorder(world)) {
            radius = (int) (world.getWorldBorder().getSize() / 2);
        }

        int x = this.random.nextInt(-radius, radius);
        int z = this.random.nextInt(-radius, radius);

        return PaperLib.getChunkAtAsync(new Location(world, x, 100, z)).thenCompose(chunk -> {
            int y = chunk.getWorld().getHighestBlockYAt(x, z);

            if (world.getEnvironment() == World.Environment.NETHER) {
                y = this.random.nextInt(125);
            }

            Location generatedLocation = new Location(world, x, y, z).add(0.5, 1, 0.5);

            if (this.isSafeLocation(chunk, generatedLocation)) {
                return CompletableFuture.completedFuture(generatedLocation);
            }

            return this.getSafeRandomLocation(world, attemptCount - 1);
        });
    }

    private boolean isSafeLocation(Chunk chunk, Location location) {
        if (location == null || location.getWorld() == null) {
            return false;
        }

        World world = chunk.getWorld();
        Block block = world.getBlockAt(location);
        Block blockAbove = world.getBlockAt(location.clone().add(0, 1, 0));
        Block blockFloor = world.getBlockAt(location.clone().add(0, -1, 0));

        if (UNSAFE_BLOCKS.contains(blockFloor.getType())) {
            return false;
        }

        if (!AIR_BLOCKS.contains(block.getType()) || !AIR_BLOCKS.contains(blockAbove.getType())) {
            return false;
        }

        if (!blockFloor.getType().isSolid()) {
            return false;
        }

        return switch (world.getEnvironment()) {
            case NORMAL, THE_END -> true;
            case NETHER -> location.getY() <= 127;
            default -> false;
        };
    }

    private boolean hasSetWorldBorder(World world) {
        return world.getWorldBorder().getSize() < DEFAULT_WORLD_BORDER_SIZE;
    }
}
