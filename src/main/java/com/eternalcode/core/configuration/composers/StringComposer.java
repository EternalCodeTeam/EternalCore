package com.eternalcode.core.configuration.composers;

import panda.std.Result;

public class StringComposer implements SimpleComposer<String>{

    @Override
    public Result<String, Exception> deserialize(String source) {
        return Result.ok("\"" + source + "\"");
    }

    @Override
    public Result<String, Exception> serialize(String entity) {
        if (!entity.startsWith("\"") || !entity.endsWith("\"") || entity.length() < 2) {
            return Result.error(new IllegalStateException("Brakuje \" w confingu!"));
        }

        return Result.ok(entity.substring(1, entity.length() - 1));
    }
}
