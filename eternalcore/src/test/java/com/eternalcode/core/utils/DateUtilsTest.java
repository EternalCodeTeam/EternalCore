package com.eternalcode.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    @Test
    void milliseconds() {
        assertEquals("5ms", DateUtils.durationToString(5L));
    }

    @Test
    void seconds() {
        assertEquals("5s", DateUtils.durationToString(5000L));
    }

    @Test
    void minutes() {
        assertEquals("5m", DateUtils.durationToString(300000L));
    }

    @Test
    void hours() {
        assertEquals("8h20m", DateUtils.durationToString(30000000L));
    }
}
