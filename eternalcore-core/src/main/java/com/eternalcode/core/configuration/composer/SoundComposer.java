package com.eternalcode.core.configuration.composer;

import org.bukkit.Sound;
import panda.std.Result;

public class SoundComposer implements SimpleComposer<Sound> {

    @Override
    public Result<Sound, Exception> deserialize(String source) {
        try {
            return Result.ok(Sound.valueOf(source));
        } catch (IllegalArgumentException argumentException) {
            return Result.error(argumentException);
        }
    }

    @Override
    public Result<String, Exception> serialize(Sound entity) {
        return Result.ok(entity.name());
    }

}
