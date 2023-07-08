package com.eternalcode.core.feature.rtp;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class RTPService {

    private static final List<Material> UNSAFE_BLOCKS = List.of(
        Material.LAVA,
        Material.WATER,
        Material.CACTUS,
        Material.FIRE,
        Material.COBWEB,
        Material.SWEET_BERRY_BUSH,
        Material.MAGMA_BLOCK
    );

    private final PluginConfiguration.Teleport teleportConfiguration;

    private final Random random = new Random();

    public RTPService(PluginConfiguration.Teleport teleportConfiguration) {
        this.teleportConfiguration = teleportConfiguration;
    }

    public void randomTeleportPlayer(Player player) {
        int radius = this.teleportConfiguration.randomTeleportRadius;
        World world = player.getWorld();
        Location safeLocation = this.getSafeLocation(world, radius);
        player.teleport(safeLocation);
    }

    public Location getSafeLocation(World world, int radius) {
        int maxTries = 100;

        for (int currentTry = 0; currentTry < maxTries; currentTry++) {
            int x = this.random.nextInt(radius * 2 + 1) - radius;
            int z = this.random.nextInt(radius * 2 + 1) - radius;
            int y = world.getHighestBlockYAt(x, z);

            if (world.getEnvironment() == World.Environment.NETHER) {
                y = this.random.nextInt(125);
            }

            Location location = new Location(world, x, y, z).add(0.5, 1, 0.5);

            if (this.isSafeLocation(location)) {

                return location;
            }
        }

        return world.getSpawnLocation();
    }

    public boolean isSafeLocation(Location location) {
        if (location == null || location.getWorld() == null) {
            return false;
        }

        World world = location.getWorld();
        Block block = world.getBlockAt(location);
        Block blockAbove = world.getBlockAt(location.clone().add(0, 1, 0));
        Block blockBelow = world.getBlockAt(location.clone().add(0, -1, 0));

        if (this.hasNearbyUnsafeBlock(location)) {
            return false;
        }

        if (UNSAFE_BLOCKS.contains(blockBelow.getType())) {
            return false;
        }

        if (block.getType().isSolid() && blockAbove.getType().isSolid()) {
            return false;
        }

        if (!blockBelow.getType().isSolid()) {
            return false;
        }

        return switch (world.getEnvironment()) {
            case NORMAL, THE_END -> true;
            case NETHER -> location.getY() <= 127;
            default -> false;
        };
    }

    private boolean hasNearbyUnsafeBlock(Location location) {
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block[] nearbyBlocks = new Block[]{
            world.getBlockAt(x - 1, y, z),
            world.getBlockAt(x + 1, y, z),
            world.getBlockAt(x, y - 1, z),
            world.getBlockAt(x, y + 1, z),
            world.getBlockAt(x, y, z - 1),
            world.getBlockAt(x, y, z + 1)
        };

        for (Block nearbyBlock : nearbyBlocks) {
            if (UNSAFE_BLOCKS.contains(nearbyBlock.getType())) {
                return true;
            }
        }

        return false;
    }
}
