package com.eternalcode.core.util;

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

        String[] words = text.split(SPACE);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1));
                }
            }

            if (i < words.length - 1) {
                result.append(SPACE);
            }
        }

        return result.toString();
    }
}
