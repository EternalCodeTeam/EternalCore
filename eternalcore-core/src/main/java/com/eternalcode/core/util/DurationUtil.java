package com.eternalcode.core.util;

import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.commons.time.TemporalAmountParser;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public class DurationUtil {

    public static final Duration ONE_SECOND = Duration.ofSeconds(1);
    private static final Pattern TOKEN = Pattern.compile("\\s*(\\d+)\\s*([a-zA-Z]+)\\s*");

    private static final TemporalAmountParser<Duration> WITHOUT_MILLIS_FORMAT = new DurationParser()
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS)
        .withRounded(ChronoUnit.MILLIS, RoundingMode.UP);
    private static final TemporalAmountParser<Duration> STANDARD_FORMAT = new DurationParser()
        .withUnit("d", ChronoUnit.DAYS)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("ms", ChronoUnit.MILLIS);

    public DurationUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static String format(Duration duration, boolean removeMillis) {
        String formatted;
        if (removeMillis) {
            if (duration.toMillis() < ONE_SECOND.toMillis()) {
                return "0s";
            }
            formatted = WITHOUT_MILLIS_FORMAT.format(duration);
        }
        else {
            formatted = STANDARD_FORMAT.format(duration);
        }
        return TOKEN.matcher(formatted).replaceAll("$1$2 ").trim();
    }

    public static String format(Duration duration) {
        return format(duration, false);
    }
}
