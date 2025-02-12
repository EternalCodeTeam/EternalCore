package com.eternalcode.core.configuration.composer;

import com.eternalcode.commons.time.DurationParser;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import panda.std.Result;

public class DurationComposer implements SimpleComposer<Duration> {

    @Override
    public Result<Duration, Exception> deserialize(String source) {
        if (source.equals("0s")) {
            return Result.ok(Duration.ZERO);
        }

        return Result.supplyThrowing(DateTimeParseException.class, () -> DurationParser.TIME_UNITS.parse(source));
    }

    @Override
    public Result<String, Exception> serialize(Duration entity) {
        if (entity.isZero()) {
            return Result.ok("0s");
        }

        return Result.ok(DurationParser.TIME_UNITS.format(entity));
    }
}
