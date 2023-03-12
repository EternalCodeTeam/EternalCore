package com.eternalcode.core.configuration.composer;

import org.bukkit.Sound;
import panda.std.Result;

public class SoundComposer implements SimpleComposer<Sound> {

    @Override
    public Result<Sound, Exception> deserialize(String source) {
        return Result.ok(Sound.valueOf(source));
    }

    @Override
    public Result<String, Exception> serialize(Sound entity) {
        return Result.ok(entity.getKey().getNamespace());
    }
}
