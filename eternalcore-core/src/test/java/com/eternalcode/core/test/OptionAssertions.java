package com.eternalcode.core.test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class OptionAssertions {

    private OptionAssertions() {
    }

    public static <T> T assertOptionPresent(Optional<T> option) {
        assertTrue(option.isPresent());
        return option.get();
    }

    public static void assertOptionEmpty(Optional<?> option) {
        assertTrue(option.isEmpty());
    }

}
