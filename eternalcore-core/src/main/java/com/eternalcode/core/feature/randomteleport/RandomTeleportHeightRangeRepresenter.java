package com.eternalcode.core.feature.randomteleport;

import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class RandomTeleportHeightRangeRepresenter {

    public int minY;
    public int maxY;

    public RandomTeleportHeightRangeRepresenter(int minY, int maxY) {
        this.minY = minY;
        this.maxY = maxY;
    }

    public RandomTeleportHeightRangeRepresenter() {
    }

    public static RandomTeleportHeightRangeRepresenter of(int minY, int maxY) {
        return new RandomTeleportHeightRangeRepresenter(minY, maxY);
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMaxY() {
        return this.maxY;
    }
}
