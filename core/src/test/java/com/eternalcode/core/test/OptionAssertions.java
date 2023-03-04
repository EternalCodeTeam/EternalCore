package com.eternalcode.core.test;

import panda.std.Option;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class OptionAssertions {

    private OptionAssertions() {
    }

    public static <T> T assertOptionPresent(Option<T> option) {
        assertTrue(option.isPresent());
        return option.get();
    }

    public static void assertOptionEmpty(Option<?> option) {
        assertTrue(option.isEmpty());
    }

}
