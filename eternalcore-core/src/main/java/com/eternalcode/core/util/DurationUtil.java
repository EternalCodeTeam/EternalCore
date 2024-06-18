package com.eternalcode.core.util;

import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.commons.time.TemporalAmountParser;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationUtil {

    private static final TemporalAmountParser<Duration> WITHOUT_MILLS_FORMAT = new DurationParser()
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS)
        .roundOff(ChronoUnit.MILLIS);

    private static final TemporalAmountParser<Duration> STANDARD_FORMAT = new DurationParser()
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS);

    public DurationUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static String format(Duration duration, boolean removeMills) {
        if (removeMills) {
            return WITHOUT_MILLS_FORMAT.format(duration);
        }

        return STANDARD_FORMAT.format(duration);
    }
}
