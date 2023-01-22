package com.eternalcode.core.configuration.composer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import panda.std.Result;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DurationComposerTest {

    DurationComposer composer = new DurationComposer();

    @CsvSource({
        "5400, 1h30m",
        "3600, 1h",
        "1800, 30m",
        "60, 1m",
        "1, 1s",
        "5401, 1h30m1s",
        "3601, 1h1s",
        "61, 1m1s",
        "3661, 1h1m1s",
    })
    @ParameterizedTest
    void testDeserialize(long expected, String source) {
        Result<Duration, Exception> deserialize = this.composer.deserialize(source);

        assertTrue(deserialize.isOk());
        assertEquals(expected, deserialize.get().getSeconds());
    }

    @CsvSource({
        "1h30m, 5400",
        "1h, 3600",
        "30m, 1800",
        "1m, 60",
        "1s, 1",
        "1h30m1s, 5401",
        "1h1s, 3601",
        "1m1s, 61",
        "1h1m1s, 3661",
    })
    @ParameterizedTest
    void testSerialize(String expected, long source) {
        Result<String, Exception> serialize = this.composer.serialize(Duration.ofSeconds(source));

        assertTrue(serialize.isOk());
        assertEquals(expected, serialize.get());
    }

}
