package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.core.util.DurationUtil;

import java.util.Locale;
import java.util.Objects;

public final class WarnEscalationParser {

    private static final String PERMANENT_KEYWORD = "permanent";

    private WarnEscalationParser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static WarnEscalation parse(String raw) {
        Objects.requireNonNull(raw, "raw cannot be null");

        String[] parts = raw.split(":", 2);
        WarnEscalationAction action = WarnEscalationAction.valueOf(parts[0].trim().toUpperCase(Locale.ROOT));

        if (action == WarnEscalationAction.KICK) {
            return new WarnEscalation(action, null);
        }

        if (parts.length < 2) {
            throw new IllegalArgumentException("Warn escalation \"" + raw + "\" is missing a duration (use \"" + PERMANENT_KEYWORD + "\" for permanent)");
        }

        String durationPart = parts[1].trim();

        if (durationPart.equalsIgnoreCase(PERMANENT_KEYWORD)) {
            return new WarnEscalation(action, null);
        }

        return new WarnEscalation(action, DurationUtil.parse(durationPart));
    }
}
