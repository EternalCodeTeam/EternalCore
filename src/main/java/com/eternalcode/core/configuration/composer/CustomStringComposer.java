package com.eternalcode.core.configuration.composer;

import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;
import panda.std.Result;

public class CustomStringComposer implements Composer<String>, SimpleSerializer<String>, SimpleDeserializer<String> {

    @Override
    public Result<String, Exception> deserialize(String text) {
        if (!text.startsWith("\"") || !text.endsWith("\"")) {
            return Result.error(new IllegalStateException("Brakuje \" w confingu!"));
        }

        return Result.ok(text.substring(1, text.length() - 1));
    }

    @Override
    public Result<String, Exception> serialize(String entity) {
        return Result.ok(entity.toString());
    }
}
