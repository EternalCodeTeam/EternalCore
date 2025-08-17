package com.eternalcode.core.util;

import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.commons.time.TemporalAmountParser;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public class DurationUtil {

    public static final Duration ONE_SECOND = Duration.ofSeconds(1);

    private static final Pattern DURATION_REFORMAT_PATTERN = Pattern.compile("([0-9]+)([dhms]+)");
    private static final String REPLACEMENT_WITH_SPACE = "$1$2 ";

    private static final TemporalAmountParser<Duration> WITHOUT_MILLIS_FORMAT = new DurationParser()
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS)
        .withRounded(ChronoUnit.MILLIS, RoundingMode.UP);
    private static final TemporalAmountParser<Duration> STANDARD_FORMAT = new DurationParser()
        .withUnit("ms", ChronoUnit.MILLIS)
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS);

    public DurationUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static String format(Duration duration, boolean removeMillis) {
        if (removeMillis) {
            if (duration.toMillis() < ONE_SECOND.toMillis()) {
                return "0s";
            }
            return reformat(WITHOUT_MILLIS_FORMAT.format(duration));
        }
        return reformat(STANDARD_FORMAT.format(duration));
    }

    private static String reformat(String formatted) {
        return DURATION_REFORMAT_PATTERN.matcher(formatted).replaceAll(REPLACEMENT_WITH_SPACE).trim();
    }

    public static String format(Duration duration) {
        return format(duration, false);
    }
}
