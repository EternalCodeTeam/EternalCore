package com.eternalcode.core.configuration.composer;

import org.bukkit.Material;
import panda.std.Result;

public class MaterialComposer implements SimpleComposer<Material>  {

    @Override
    public Result<Material, Exception> deserialize(String source) {
        return Result.supplyThrowing(IllegalArgumentException.class, () -> Material.valueOf(source.toUpperCase()));
    }

    @Override
    public Result<String, Exception> serialize(Material entity) {
        return Result.ok(entity.name().toLowerCase());
    }

}
