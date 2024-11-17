package com.eternalcode.core.feature.randomteleport;

import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class RandomTeleportRadiusRepresenterImpl implements RandomTeleportRadiusRepresenter {

    public int minX;
    public int maxX;
    public int minZ;
    public int maxZ;

    public RandomTeleportRadiusRepresenterImpl(int minX, int maxX, int minZ, int maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public RandomTeleportRadiusRepresenterImpl() {
    }

    @Override
    public int getMinX() {
        return this.minX;
    }

    @Override
    public int getMaxX() {
        return this.maxX;
    }

    @Override
    public int getMinZ() {
        return this.minZ;
    }

    @Override
    public int getMaxZ() {
        return this.maxZ;
    }
}
