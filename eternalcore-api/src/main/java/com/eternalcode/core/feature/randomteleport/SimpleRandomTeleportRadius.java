package com.eternalcode.core.feature.randomteleport;

record SimpleRandomTeleportRadius(int minX, int maxX, int minZ, int maxZ) implements RandomTeleportRadius {
    public SimpleRandomTeleportRadius {
        if (minX > maxX) {
            throw new IllegalArgumentException("minX cannot be greater than maxX");
        }
        if (minZ > maxZ) {
            throw new IllegalArgumentException("minZ cannot be greater than maxZ");
        }
    }
}
