package com.eternalcode.core.placeholder;

import org.bukkit.entity.Player;

import java.util.function.Function;

public final class PlaceholderRaw implements PlaceholderReplacer {

    private final String target;
    private final Function<Player, String> replacement;

    PlaceholderRaw(String target, Function<Player, String> replacement) {
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        return text.replace("{" + this.target + "}", this.replacement.apply(targetPlayer));
    }

    public String getRawTarget() {
        return this.target;
    }

    public String rawApply(Player targetPlayer) {
        return this.replacement.apply(targetPlayer);
    }

}
