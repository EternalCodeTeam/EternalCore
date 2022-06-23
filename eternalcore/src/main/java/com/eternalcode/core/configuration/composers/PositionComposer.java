package com.eternalcode.core.configuration.composers;

import com.eternalcode.core.shared.Position;
import panda.std.Result;

public class PositionComposer implements SimpleComposer<Position> {

    @Override
    public Result<Position, Exception> deserialize(String source) {
        String[] split = source.split(";");

        return Result.ok(new Position(
            Double.parseDouble(split[0]),
            Double.parseDouble(split[1]),
            Double.parseDouble(split[2]),
            Float.parseFloat(split[3]),
            Float.parseFloat(split[4]),
            split[5])
        );
    }

    @Override
    public Result<String, Exception> serialize(Position entity) {
        String world = "world";

        if (entity.getWorld() != null) {
            world = entity.getWorld();
        }

        return Result.ok(entity.getX() +
            ";" + entity.getY() +
            ";" + entity.getZ() +
            ";" + entity.getYaw() +
            ";" + entity.getPitch() +
            ";" + world);
    }
}
