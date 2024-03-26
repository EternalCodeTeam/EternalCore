package com.eternalcode.core.feature.home;

import static com.eternalcode.core.feature.home.HomePlaceholderSetup.homesLeft;
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

    @ParameterizedTest
    @DisplayName("Test homes left")
    @CsvSource({
        "0, 0, 0",
        "5, 0, 5",
        "5, 5, 0",
    })
    void testHomesLeft(int homesLimit, int amountOfHomes, String expectedHomesLeft) {
        assertEquals(expectedHomesLeft, homesLeft(homesLimit, amountOfHomes));
    }


    @ParameterizedTest
    @DisplayName("Test owned homes formatting")
    @MethodSource("homeList")
    void testOwnedHomes(Collection<String> homes, String expectedHomesList) {
        assertEquals(expectedHomesList, homes.stream().collect(Collectors.joining(", ")));
    }

    private static Stream<Arguments> homeList() {
        return Stream.of(
            Arguments.of(List.of(), ""),
            Arguments.of(List.of("Cave"), "Cave"),
            Arguments.of(List.of("Meadow", "Igloo", "Garden"), "Meadow, Igloo, Garden"),
            Arguments.of(List.of("Meadow", "Igloo", "Garden", "Cave"), "Meadow, Igloo, Garden, Cave")
        );
    }
}
