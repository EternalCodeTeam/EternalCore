package com.eternalcode.core.adventure.resolver;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.tag.Modifying;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import solar.squares.pixelwidth.utils.CenterAPI;

public class CenterTagResolver implements Modifying {

    public static final String TAG_NAME = "center";

    public static final TagResolver RESOLVER = TagResolver.resolver(TAG_NAME, new CenterTagResolver());

    @Override
    public Component apply(@NotNull Component current, int depth) {
        if (current instanceof TextComponent component && !((TextComponent) current).content().isBlank()) {
            return CenterAPI.center(component);
        }

        return Component.empty().mergeStyle(current);
    }
}
