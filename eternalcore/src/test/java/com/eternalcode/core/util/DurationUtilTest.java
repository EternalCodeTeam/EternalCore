package com.eternalcode.core.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationUtilTest {

    @Test
    void millisecondsLegacy() {
        assertEquals("5ms", DurationUtil.durationToString(5L));
    }

    @Test
    void secondsLegacy() {
        assertEquals("5s", DurationUtil.durationToString(5000L));
    }

    @Test
    void minutesLegacy() {
        assertEquals("5m", DurationUtil.durationToString(300000L));
    }

    @Test
    void hoursLegacy() {
        assertEquals("8h20m", DurationUtil.durationToString(30000000L));
    }


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
