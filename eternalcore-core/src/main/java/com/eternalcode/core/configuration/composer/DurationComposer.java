package com.eternalcode.core.configuration.composer;

import dev.rollczi.litecommands.time.DurationParser;
import panda.std.Result;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DurationComposer implements SimpleComposer<Duration> {

    @Override
    public Result<Duration, Exception> deserialize(String source) {
        return Result.supplyThrowing(DateTimeParseException.class, () -> DurationParser.TIME_UNITS.parse(source));
    }

    @Override
    public Result<String, Exception> serialize(Duration entity) {
        return Result.ok(DurationParser.TIME_UNITS.format(entity));
    }

}
