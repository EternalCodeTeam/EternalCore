package com.eternalcode.core.util;

import com.cryptomorin.xseries.XPotion;
import com.cryptomorin.xseries.XTag;
import org.bukkit.potion.PotionEffectType;

/**
 * XSeries is controversial, but it provides long-term mappings
 * that support multiple Minecraft versions reliably.
 * <p>
 * This utility uses XPotion from XSeries to detect negative potion effects (debuffs).
 * It helps keep compatibility and simplifies effect management across versions.
 * <p>
 * <u>Important:</u> Because potion effect names have changed between Minecraft versions
 * from 1.17.1 (our support target) up to 1.21.7 (and later),
 * I decided to use XSeries to handle these naming differences consistently.
 * <p>
 * This special utility class was created to avoid overusing XSeries directly
 * throughout the codebase and to isolate its usage in one dedicated place.
 */
public final class PotionEffectUtil {

    public PotionEffectUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isNegativeEffect(PotionEffectType effectType) {
        if (effectType == null) {
            return false;
        }

        XPotion xpotion = XPotion.of(effectType);

        return XTag.DEBUFFS.isTagged(xpotion);
    }
}
