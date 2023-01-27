package com.eternalcode.core.util;

import java.time.Duration;

public final class DurationUtil {

    private DurationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String format(Duration duration) {
        return format(duration, true);
    }

    public static String format(Duration duration, boolean removeMillis) {
        if (removeMillis) {
            duration = Duration.ofSeconds(duration.toSeconds());
        }

        return duration.toString()
            .substring(2)
            .replaceAll("(\\d[HMS])(?!$)", "$1 ")
            .toLowerCase();
    }

}
