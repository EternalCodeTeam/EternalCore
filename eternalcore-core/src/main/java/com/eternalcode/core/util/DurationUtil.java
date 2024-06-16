package com.eternalcode.core.util;

import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.commons.time.TemporalAmountParser;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationUtil {

    private static final TemporalAmountParser<Duration> WITHOUT_MILLS = new DurationParser()
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS)
        .roundOff(ChronoUnit.MILLIS);

    public DurationUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }


    public static String format(Duration duration) {
        if (duration.toMillis() < 1000) {
            return "0s";
        }

        return WITHOUT_MILLS.format(duration);
    }
}
