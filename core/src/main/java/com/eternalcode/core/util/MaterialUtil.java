package com.eternalcode.core.util;

import org.bukkit.Material;
import panda.utilities.StringUtils;

public final class MaterialUtil {

    private MaterialUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String format(Material material) {
        return StringUtils.capitalize(material.name().toLowerCase().replace("_", " "));
    }
}
