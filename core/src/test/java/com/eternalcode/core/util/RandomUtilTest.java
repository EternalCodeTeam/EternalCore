package com.eternalcode.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import panda.std.Option;

import static org.junit.jupiter.api.Assertions.*;

class RandomUtilTest {

    @Test
    void testRandomElement() {
        Collection<String> strings = Arrays.asList("a", "b", "c", "d", "e");
        Option<String> randomElement = RandomUtil.randomElement(strings);

        assertTrue(randomElement.isPresent());
        assertTrue(strings.contains(randomElement.get()));
    }

    @Test
    void testRandomElementEmptyCollection() {
        List<String> emptyList = List.of();
        Option<String> randomElement = RandomUtil.randomElement(emptyList);
        
        assertTrue(randomElement.isEmpty());
    }
}
