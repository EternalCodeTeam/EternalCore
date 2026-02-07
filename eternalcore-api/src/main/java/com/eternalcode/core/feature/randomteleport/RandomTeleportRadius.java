package com.eternalcode.core.feature.randomteleport;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface RandomTeleportRadius {
    int maxZ();
    int minZ();
    int maxX();
    int minX();

    static RandomTeleportRadius of(int minX, int maxX, int minZ, int maxZ) {
        return new SimpleRandomTeleportRadius(minX, maxX, minZ, maxZ);
    }

    static RandomTeleportRadius of(int radius) {
        return new SimpleRandomTeleportRadius(-radius, radius, -radius, radius);
    }

}
