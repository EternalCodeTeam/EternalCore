package com.eternalcode.core.placeholder;

import org.bukkit.entity.Player;

import java.util.function.Function;

public record PlaceholderRaw(String target, Function<Player, String> replacement) implements Placeholder {

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
