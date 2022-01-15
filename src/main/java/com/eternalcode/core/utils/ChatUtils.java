/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.utils;


import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import panda.std.stream.PandaStream;

import java.util.List;

public final class ChatUtils {
    private static final LegacyComponentSerializer serializer = PaperComponents.legacySectionSerializer();

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> color(Iterable<String> texts) {
        return PandaStream.of(texts)
            .map(ChatUtils::color)
            .toList();
    }

    public static Component component(String text) {
        return serializer.deserialize(color(text));
    }

    public static List<Component> component(Iterable<String> texts) {
        return PandaStream.of(texts)
            .map(ChatUtils::component)
            .toList();
    }
}
