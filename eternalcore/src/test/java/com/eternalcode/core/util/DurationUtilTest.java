package com.eternalcode.core.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationUtilTest {

    @Test
    void milliseconds() {
        assertEquals("0s", DurationUtil.format(Duration.ofMillis(5L)));
        assertEquals("0.005s", DurationUtil.format(Duration.ofMillis(5L), false));
    }

    @Test
    void seconds() {
        assertEquals("5s", DurationUtil.format(Duration.ofSeconds(5L)));
    }

    @Test
    void minutes() {
        assertEquals("5m", DurationUtil.format(Duration.ofMinutes(5L)));
    }

    @Test
    void hours() {
        assertEquals("8h 20m", DurationUtil.format(Duration.ofHours(8L).plus(20, ChronoUnit.MINUTES)));
    }
}
