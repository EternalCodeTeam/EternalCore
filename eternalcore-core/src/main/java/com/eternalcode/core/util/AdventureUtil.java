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

    public static Sound.Type toAdventure(org.bukkit.Sound sound) {
        return new SoundAdapter(sound);
    }

    private record SoundAdapter(org.bukkit.Sound sound) implements Sound.Type {

        @Override
        public @NotNull Key key() {
            String namespace = this.sound.getKey().getNamespace();
            String key = this.sound.getKey().getKey();
            return Key.key(namespace, key);
        }
    }
}
