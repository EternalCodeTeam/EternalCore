package com.eternalcode.core.util;

import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.commons.time.TemporalAmountParser;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public final class DurationUtil {

    private static final Duration SECOND = Duration.ofSeconds(1);
    private static final Pattern REFORMAT_PATTERN = Pattern.compile("(\\d+)([dhms]+)");
    private static final String REFORMAT_REPLACEMENT = "$1$2 ";

    private static final TemporalAmountParser<Duration> DURATION_NO_MILLIS = new DurationParser()
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS)
        .withRounded(ChronoUnit.MILLIS, RoundingMode.UP);

    private static final TemporalAmountParser<Duration> DURATION_STANDARD = new DurationParser()
        .withUnit("ms", ChronoUnit.MILLIS)
        .withUnit("s", ChronoUnit.SECONDS)
        .withUnit("m", ChronoUnit.MINUTES)
        .withUnit("h", ChronoUnit.HOURS)
        .withUnit("d", ChronoUnit.DAYS);

    private DurationUtil() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static String format(Duration duration) {
        return format(duration, false);
    }

    public static String format(Duration duration, boolean removeMillis) {
        if (removeMillis) {
            if (duration.compareTo(SECOND) < 0) {
                return "0s";
            }
            return reformat(DURATION_NO_MILLIS.format(duration));
        }
        return reformat(DURATION_STANDARD.format(duration));
    }

    private static String reformat(String input) {
        return REFORMAT_PATTERN.matcher(input).replaceAll(REFORMAT_REPLACEMENT).trim();
    }
}
