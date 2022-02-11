package com.eternalcode.core.configuration.composers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import panda.std.Result;

public class LocationComposer implements SimpleComposer<Location> {

    @Override
    public Result<Location, Exception> deserialize(String source) {
        String[] split = source.split(";");

        return Result.ok(new Location(Bukkit.getWorld(split[0]),
            Double.parseDouble(split[1]),
            Double.parseDouble(split[2]),
            Double.parseDouble(split[3]),
            Float.parseFloat(split[4]),
            Float.parseFloat(split[5])));
    }

    @Override
    public Result<String, Exception> serialize(Location entity) {
        String world = "world";

        if (entity.getWorld() != null){
            world = entity.getWorld().getName();
        }

        return Result.ok((world +
            ";" +entity.getBlockX()) +
            ";" + entity.getBlockY() +
            ";" + entity.getBlockZ() +
            ";" + entity.getYaw() +
            ";" + entity.getPitch());
    }
}
