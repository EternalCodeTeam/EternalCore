package com.eternalcode.core.util;

import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.commons.time.TemporalAmountParser;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public final class DurationUtil {

    private static final Duration ONE_SECOND = Duration.ofSeconds(1);
    private static final long ONE_SECOND_MILLIS = ONE_SECOND.toMillis();
    private static final String ZERO_SECONDS = "0s";
    private static final Pattern UNIT_SPACING_PATTERN = Pattern.compile("(?<=[A-Za-z])(?=\\d)");

    private static final TemporalAmountParser<Duration> WITHOUT_MILLIS_FORMAT = new DurationParser()
        .withUnit("d", ChronoUnit.DAYS)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("s", ChronoUnit.SECONDS)
        .withRounded(ChronoUnit.MILLIS, RoundingMode.UP);

    private static final TemporalAmountParser<Duration> STANDARD_FORMAT = new DurationParser()
        .withUnit("d", ChronoUnit.DAYS)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("ms", ChronoUnit.MILLIS);

    private DurationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String format(Duration duration) {
        return format(duration, false);
    }

    public static String format(Duration duration, boolean removeMillis) {
        if (duration == null) {
            throw new IllegalArgumentException("Duration cannot be null");
        }

        String formatted;
        if (removeMillis) {
            if (duration.toMillis() < ONE_SECOND_MILLIS) {
                return addUnitSpacing(ZERO_SECONDS);
            }
            formatted = WITHOUT_MILLIS_FORMAT.format(duration);
        }
        else {
            formatted = STANDARD_FORMAT.format(duration);
        }

        return addUnitSpacing(formatted);
    }

    private static String addUnitSpacing(String formatted) {
        return UNIT_SPACING_PATTERN.matcher(formatted).replaceAll(" ");
    }
}
