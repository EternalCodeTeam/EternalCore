package com.eternalcode.core.placeholder;

import org.bukkit.entity.Player;

import java.util.function.Function;

public interface PlayerPlaceholderReplacer {

    String apply(String text, Player targetPlayer);

    static PlayerPlaceholderReplacer of(String target, String replacement) {
        return new SimplePlayerPlaceholderReplacer(target, player -> replacement);
    }

    static PlayerPlaceholderReplacer of(String target, Function<Player, String> replacement) {
        return new SimplePlayerPlaceholderReplacer(target, replacement);
    }

}
