package com.eternalcode.core.feature.randomteleport;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Contextual
public class RandomTeleportHeightRangeRepresenter {

    @Description({
        "# Specifies the minimum Y-coordinate for the defined area.",
        "# -64 is the minimum Y-coordinate in Minecraft."
    })
    public int minY;

    @Description({
        "# Specifies the maximum Y-coordinate for random teleportation.",
        "# 256 is the maximum build height in Minecraft."
    })
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
