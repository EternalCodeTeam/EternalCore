package com.eternalcode.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationUtilTest {

    @DisplayName("should format duration with millis precision")
    @ParameterizedTest(name = "format({0} ms) should return \"{1}\"")
    @CsvSource({
        "1000, 1s",
        "61000, 1m 1s",
        "3661000, 1h 1m 1s",
        "90061000, 1d 1h 1m 1s",
        "1500, 1s 500ms"
    })
    void shouldFormatStandard(long millis, String expected) {
        Duration duration = Duration.ofMillis(millis);
        String result = DurationUtil.format(duration);
        assertEquals(expected, result);
    }

    @DisplayName("should format duration without millis")
    @ParameterizedTest(name = "format({0} ms, removeMillis=true) should return \"{1}\"")
    @CsvSource({
        "1000, 1s",
        "61000, 1m 1s",
        "3661000, 1h 1m 1s",
        "90061000, 1d 1h 1m 1s",
        "1500, 1s"
    })
    void shouldFormatWithoutMillis(long millis, String expected) {
        Duration duration = Duration.ofMillis(millis);
        String result = DurationUtil.format(duration, true);
        assertEquals(expected, result);
    }

    @DisplayName("should return 0s when duration < 1 second and removeMillis = true")
    @Test
    void shouldReturnZeroSecondsWhenBelowOneSecondAndRemoveMillisIsTrue() {
        Duration duration = Duration.ofMillis(500);
        String result = DurationUtil.format(duration, true);
        assertEquals("0s", result);
    }
}
