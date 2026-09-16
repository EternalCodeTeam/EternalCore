package com.eternalcode.core.feature.punishment.warn;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

public final class WarnEscalation {

    private final WarnEscalationAction action;
    private final Duration duration;

    public WarnEscalation(WarnEscalationAction action, Duration duration) {
        this.action = Objects.requireNonNull(action, "action cannot be null");
        this.duration = duration;
    }

    public WarnEscalationAction action() {
        return this.action;
    }

    public Optional<Duration> duration() {
        return Optional.ofNullable(this.duration);
    }
}
