package com.eternalcode.core.feature.randomteleport;

import net.dzikoysk.cdn.entity.Contextual;

@Contextual
class RandomTeleportRadiusConfig implements RandomTeleportRadius {

    int minX;
    int maxX;
    int minZ;
    int maxZ;

    RandomTeleportRadiusConfig(int minX, int maxX, int minZ, int maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    RandomTeleportRadiusConfig() {
    }

    @Override
    public int minX() {
        return this.minX;
    }

    @Override
    public int maxX() {
        return this.maxX;
    }

    @Override
    public int minZ() {
        return this.minZ;
    }

    @Override
    public int maxZ() {
        return this.maxZ;
    }
}
