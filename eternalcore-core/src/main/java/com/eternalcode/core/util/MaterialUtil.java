package com.eternalcode.core.util;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.bukkit.Material;

public final class MaterialUtil {

    private static final String UNDERSCORE = "_";
    private static final String SPACE = " ";

    private MaterialUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String format(Material material) {
        String formattedName = material.name().toLowerCase().replace(UNDERSCORE, SPACE);
        return capitalize(formattedName);
    }

    private static String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays.stream(text.split(SPACE))
            .filter(word -> !word.isEmpty())
            .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
            .collect(Collectors.joining(SPACE));
    }
}
