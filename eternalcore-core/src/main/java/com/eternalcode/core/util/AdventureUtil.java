package com.eternalcode.core.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public final class AdventureUtil {

    public final static Component RESET_ITEM = Component.text()
        .decoration(TextDecoration.ITALIC, false)
        .build();

    private AdventureUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
