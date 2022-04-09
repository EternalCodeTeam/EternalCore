package com.eternalcode.core.chat.legacy;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Legacy {
    public static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build();
}
