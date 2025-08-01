package com.eternalcode.core.feature.randomteleport;

import java.io.Serializable;

public class RandomTeleportHeightRange implements Serializable {

    int minY;
    int maxY;

    RandomTeleportHeightRange(int minY, int maxY) {
        this.minY = minY;
        this.maxY = maxY;
    }

    RandomTeleportHeightRange() {
    }

    static RandomTeleportHeightRange of(int minY, int maxY) {
        return new RandomTeleportHeightRange(minY, maxY);
    }

    int getMinY() {
        return this.minY;
    }

    int getMaxY() {
        return this.maxY;
    }
}
