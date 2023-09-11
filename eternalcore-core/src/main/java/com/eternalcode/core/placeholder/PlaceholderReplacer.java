package com.eternalcode.core.placeholder;

import org.bukkit.entity.Player;

import java.util.function.Function;

public interface PlaceholderReplacer {

    String apply(String text, Player targetPlayer);

    static PlaceholderReplacer of(String target, String replacement) {
        return new PlaceholderRaw(target, player -> replacement);
    }

    static PlaceholderReplacer of(String target, Function<Player, String> replacement) {
        return new PlaceholderRaw(target, replacement);
    }

    static PlaceholderReplacer of(String target, PlaceholderReplacer placeholder) {
        return new PlaceholderRaw(target, player -> placeholder.apply(target, player));
    }

}
