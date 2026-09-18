package com.eternalcode.core.feature.lag;

import org.bukkit.Chunk;
import org.bukkit.World;

public final class WorldStatsUtil {

    private WorldStatsUtil() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static WorldStats collect(World world) {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null");
        }

        return new WorldStats(
            world.getName(),
            world.getChunkCount(),
            world.getEntityCount(),
            countTileEntities(world),
            world.getPlayers().size()
        );
    }

    private static int countTileEntities(World world) {
        int tileEntities = 0;

        for (Chunk chunk : world.getLoadedChunks()) {
            tileEntities += chunk.getTileEntities(false).length;
        }

        return tileEntities;
    }
}
