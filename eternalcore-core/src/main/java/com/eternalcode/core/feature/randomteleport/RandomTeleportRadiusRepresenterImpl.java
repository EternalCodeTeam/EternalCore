package com.eternalcode.core.feature.randomteleport;

import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class RandomTeleportRadiusRepresenterImpl implements RandomTeleportRadiusRepresenter {

    public int minX;
    public int maxX;
    public int minZ;
    public int maxZ;

    public RandomTeleportRadiusRepresenterImpl(int minX, int maxX, int minZ, int maxZ) {
        if (minX >= maxX) {
            throw new IllegalArgumentException("minX must be less than maxX");
        }
        if (minZ >= maxZ) {
            throw new IllegalArgumentException("minZ must be less than maxZ");
        }

        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public RandomTeleportRadiusRepresenterImpl() {
    }

    /**
     * Creates a new instance of `RandomTeleportRadiusRepresenterImpl` with the specified boundaries.
     * This method is used to define a rectangular area for random teleportation.
     *
     * @param minX The minimum X-coordinate of the area.
     * @param maxX The maximum X-coordinate of the area.
     * @param minZ The minimum Z-coordinate of the area.
     * @param maxZ The maximum Z-coordinate of the area.
     * @return A new instance of `RandomTeleportRadiusRepresenterImpl` representing the specified area.
     * @throws IllegalArgumentException if `minX` is greater than or equal to `maxX` or if `minZ` is greater than or equal to `maxZ`.
     */
    public static RandomTeleportRadiusRepresenterImpl of(int minX, int maxX, int minZ, int maxZ) {
        return new RandomTeleportRadiusRepresenterImpl(minX, maxX, minZ, maxZ);
    }

    /**
     * Creates a new instance of `RandomTeleportRadiusRepresenterImpl` with a square area defined by the given radius.
     * This method is used to define a square area centered at the origin for random teleportation.
     *
     * @param radius The radius of the square area. The area will extend from `-radius` to `radius` in both X and Z directions.
     * @return A new instance of `RandomTeleportRadiusRepresenterImpl` representing the square area.
     * @throws IllegalArgumentException if `radius` is negative.
     */
    public static RandomTeleportRadiusRepresenterImpl of(int radius) {
        return new RandomTeleportRadiusRepresenterImpl(-radius, radius, -radius, radius);
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
