package com.eternalcode.core.placeholder;

import org.bukkit.entity.Player;

import java.util.function.Function;

public interface Placeholder {

    String apply(String text, Player targetPlayer);

    static Placeholder of(String target, String replacement) {
        return new PlaceholderRaw(target, player -> replacement);
    }

    static Placeholder of(String target, Function<Player, String> replacement) {
        return new PlaceholderRaw(target, replacement);
    }

    static Placeholder ofInt(String target, Function<Player, Integer> replacement) {
        return new PlaceholderRaw(target, player -> String.valueOf(replacement.apply(player)));
    }

    static Placeholder ofBoolean(String target, Function<Player, Boolean> replacement) {
        return new PlaceholderRaw(target, player -> String.valueOf(replacement.apply(player)));
    }

    static Placeholder ofLong(String target, Function<Player, Long> replacement) {
        return new PlaceholderRaw(target, player -> String.valueOf(replacement.apply(player)));
    }

    static Placeholder of(String target, Placeholder placeholder) {
        return new PlaceholderRaw(target, player -> placeholder.apply(target, player));
    }

}
