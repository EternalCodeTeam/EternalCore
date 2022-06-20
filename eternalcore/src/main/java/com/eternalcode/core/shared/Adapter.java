package com.eternalcode.core.shared;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class Adapter {

    private Adapter() {}

    public static Position convert(Location location) {
        if (location.getWorld() == null) {
            throw new IllegalStateException();
        }

        return new Position(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
    }

    public static Location convert(Position position) {
        World world = Bukkit.getWorld(position.getWorld());

        if (world == null) {
            throw new IllegalStateException();
        }

        return new Location(world, position.getX(), position.getY(), position.getZ(), position.getYaw(), position.getPitch());
    }

}
