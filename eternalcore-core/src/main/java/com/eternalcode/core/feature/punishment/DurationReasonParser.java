package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.util.DurationUtil;

import java.time.Duration;
import java.util.Objects;

public final class DurationReasonParser {

    private DurationReasonParser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Result parse(String input, String defaultReason) {
        if (input == null || input.isBlank()) {
            return new Result(null, defaultReason);
        }

        String trimmed = input.trim();
        int spaceIndex = trimmed.indexOf(' ');
        String firstToken = spaceIndex == -1 ? trimmed : trimmed.substring(0, spaceIndex);
        String rest = spaceIndex == -1 ? "" : trimmed.substring(spaceIndex + 1).trim();

        Duration duration = tryParseDuration(firstToken);

        if (duration == null) {
            return new Result(null, reasonOrDefault(trimmed, defaultReason));
        }

        return new Result(duration, reasonOrDefault(rest, defaultReason));
    }

    public static String reasonOrDefault(String reason, String defaultReason) {
        if (reason == null || reason.isBlank()) {
            return defaultReason;
        }

        return reason.trim();
    }

    private static Duration tryParseDuration(String token) {
        try {
            return DurationUtil.parse(token);
        }
        catch (RuntimeException exception) {
            return null;
        }
    }

    public record Result(Duration duration, String reason) {
    }
}
