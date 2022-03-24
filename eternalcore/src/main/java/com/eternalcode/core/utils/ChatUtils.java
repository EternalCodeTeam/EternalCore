package com.eternalcode.core.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import panda.std.stream.PandaStream;

import java.util.List;

@UtilityClass
public class ChatUtils {

    private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();

    public static Component component(String text) {
        return serializer.deserialize(text);
    }

    public static List<Component> component(Iterable<String> texts) {
        return PandaStream.of(texts).map(ChatUtils::component).toList();
    }
}
