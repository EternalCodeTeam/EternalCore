package com.eternalcode.core.feature.lag;

import org.bukkit.World;

public final class WorldStatsCollector {

    private WorldStatsCollector() {
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
            world.getTileEntityCount(),
            world.getPlayers().size()
        );
    }
}
