package com.eternalcode.core.placeholder;

import org.bukkit.entity.Player;

import java.util.function.Function;

class SimplePlayerPlaceholderReplacer implements PlayerPlaceholderReplacer {

    private final String target;
    private final Function<Player, String> replacement;

    SimplePlayerPlaceholderReplacer(String target, Function<Player, String> replacement) {
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        return text.replace(target, replacement.apply(targetPlayer));
    }

}
