package com.eternalcode.core.util;

import com.cryptomorin.xseries.XMaterial;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.bukkit.Material;

public final class MaterialUtil {

    private static final String UNDERSCORE = "_";
    private static final String SPACE = " ";
    private static final Material FALLBACK_MATERIAL = Material.STONE;
    private static final Logger LOGGER = Logger.getLogger("EternalCore");
    private static final Set<String> WARNED_MATERIALS = ConcurrentHashMap.newKeySet();

    private MaterialUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String format(Material material) {
        String formattedName = material.name().toLowerCase().replace(UNDERSCORE, SPACE);
        return capitalize(formattedName);
    }

    public static Material parseRequired(XMaterial material) {
        return parse(material).orElseGet(() -> fallback(material.name(), FALLBACK_MATERIAL));
    }

    public static Optional<Material> parse(XMaterial material) {
        return Optional.ofNullable(material.get());
    }

    public static Optional<Material> parse(String materialName) {
        return XMaterial.matchXMaterial(materialName).map(XMaterial::get);
    }

    public static Material parseOrFallback(String materialName) {
        return parse(materialName).orElseGet(() -> fallback(materialName, FALLBACK_MATERIAL));
    }

    public static void warnUnsupported(String materialName) {
        warnUnsupported(materialName, "skipping it");
    }

    private static Material fallback(String materialName, Material fallbackMaterial) {
        warnUnsupported(materialName, "using " + fallbackMaterial.name() + " instead");
        return fallbackMaterial;
    }

    private static void warnUnsupported(String materialName, String action) {
        if (!WARNED_MATERIALS.add(materialName)) {
            return;
        }

        LOGGER.warning("Material '" + materialName + "' is not supported by this server version, " + action + ".");
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
