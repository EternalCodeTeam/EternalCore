/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.utils;


import io.papermc.paper.text.PaperComponents;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import panda.std.stream.PandaStream;

import java.util.List;

@Deprecated // TODO: Adventure MiniMessage
@UtilityClass
public class ChatUtils {

    @Deprecated // Legacy Colors
    private static final LegacyComponentSerializer serializer = PaperComponents.legacySectionSerializer();

    @Deprecated // Legacy Colors
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    @Deprecated // Legacy Colors
    public static List<String> color(Iterable<String> texts) {
        return PandaStream.of(texts).map(ChatUtils::color).toList();
    }

    @Deprecated // Legacy Colors
    public static Component component(String text) {
        return serializer.deserialize(color(text));
    }

    @Deprecated // Legacy Colors
    public static List<Component> component(Iterable<String> texts) {
        return PandaStream.of(texts).map(ChatUtils::component).toList();
    }

}
