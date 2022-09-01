package com.eternalcode.core.configuration.composer;

import panda.std.Result;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DurationComposer implements SimpleComposer<Duration> {

    @Override
    public Result<Duration, Exception> deserialize(String source) {
        return Result.supplyThrowing(DateTimeParseException.class, () -> Duration.parse("PT" + source.toUpperCase(Locale.ROOT)));
    }

    @Override
    public Result<String, Exception> serialize(Duration entity) {
        return Result.ok(entity.toString().substring(2).toLowerCase(Locale.ROOT));
    }

}
