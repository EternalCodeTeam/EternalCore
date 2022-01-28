/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.utils;

import java.util.concurrent.TimeUnit;

public final class DateUtils {

    public static String durationToString(long time) {
        if (time <= 0L) {
            return "0s";
        }

        final StringBuilder stringBuilder = new StringBuilder();
        final long days = TimeUnit.MILLISECONDS.toDays(time);
        final long hours = TimeUnit.MILLISECONDS.toHours(time) % 24L;
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60L;
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60L;
        final long millis = time % 1000L;

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
