package com.eternalcode.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import panda.std.stream.PandaStream;

import java.util.List;

public final class ChatUtils {

    private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();

    private ChatUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Component component(String text) {
        return serializer.deserialize(text);
    }

    public static List<Component> component(Iterable<String> texts) {
        return PandaStream.of(texts).map(ChatUtils::component).toList();
    }
}
