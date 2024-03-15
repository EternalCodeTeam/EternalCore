package com.eternalcode.core.feature.home;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class HomePlaceholderSetupTest {

    static Stream<Arguments> homeList() {
        return Stream.of(
            Arguments.of(List.of(), ""),
            Arguments.of(List.of("home1"), "home1"),
            Arguments.of(List.of("home1", "home2", "home3"), "home1, home2, home3")
        );
    }

    @ParameterizedTest
    @DisplayName("Test homes left")
    @CsvSource({
        "0, 0, 0",
        "5, 0, 5",
        "5, 7, 0",
        "5, 5, 0"
    })
    void testHomesLeft(int homesLimit, int amountOfHomes, String expectedHomesLeft) {
        assertEquals(expectedHomesLeft, String.valueOf(Math.max(homesLimit - amountOfHomes, 0)));
    }

    @ParameterizedTest
    @DisplayName("Test owned homes formatting")
    @MethodSource("homeList")
    void testOwnedHomes(Collection<String> homes, String expectedHomesList) {
        assertEquals(expectedHomesList, homes.stream().collect(Collectors.joining(", ")));
    }
}
