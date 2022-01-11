package com.eternalcode.core.utils;

public final class DateUtils {

    public static String durationToString(long time) {
        time -= System.currentTimeMillis();

        if (time <= 0L)
            return "0s";

        final StringBuilder stringBuilder = new StringBuilder();
        final long days = time / 86400000L;
        final long hours = (time / 3600000L) % 24L;
        final long minutes = (time / 60000L) % 60L;
        final long seconds = (time / 1000L) % 60L;

        if (days > 0)
            stringBuilder.append(days)
                .append("d");

        if (hours > 0)
            stringBuilder.append(hours)
                .append("h");

        if (minutes > 0)
            stringBuilder.append(minutes)
                .append("m");

        if (seconds > 0)
            stringBuilder.append(seconds)
                .append("s");

        return stringBuilder.toString();
    }
}
