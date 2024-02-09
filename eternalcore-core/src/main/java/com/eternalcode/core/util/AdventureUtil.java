package com.eternalcode.core.util;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

public final class AdventureUtil {

    public static final Component RESET_ITEM = Component.text()
        .decoration(TextDecoration.ITALIC, false)
        .build();

    private AdventureUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
