package com.eternalcode.core.placeholder;

import org.bukkit.entity.Player;

import java.util.function.Function;

class StaticValuePlaceholder implements NamedPlaceholder {

    private final String name;
    private final Function<Player, String> replacement;

    StaticValuePlaceholder(String name, Function<Player, String> replacement) {
        this.name = name;
        this.replacement = replacement;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        return text.replace("{" + this.name + "}", this.replacement.apply(targetPlayer));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String provideValue(Player targetPlayer) {
        return this.replacement.apply(targetPlayer);
    }

}
