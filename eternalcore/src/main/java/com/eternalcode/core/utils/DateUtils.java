package com.eternalcode.core.utils;

import java.util.concurrent.TimeUnit;

public final class DateUtils {

    private DateUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String durationToString(long time) {
        if (time <= 0L) {
            return "0s";
        }

        StringBuilder stringBuilder = new StringBuilder();
        long days = TimeUnit.MILLISECONDS.toDays(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time) % 24L;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60L;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60L;
        long millis = time % 1000L;

        if (days > 0) {
            stringBuilder.append(days)
                .append("d");
        }

        if (hours > 0) {
            stringBuilder.append(hours)
                .append("h");
        }

        if (minutes > 0) {
            stringBuilder.append(minutes)
                .append("m");
        }

        if (seconds > 0) {
            stringBuilder.append(seconds)
                .append("s");
        }

        if (stringBuilder.isEmpty()) {
            stringBuilder.append(millis)
                .append("ms");
        }

        return stringBuilder.toString();
    }
}
