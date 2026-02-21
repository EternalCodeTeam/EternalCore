package com.eternalcode.core.placeholder;

import org.bukkit.entity.Player;

public interface NamedPlaceholder extends Placeholder {

    @Override
    default String apply(String text, Player targetPlayer) {
        return text.replace("{" + this.getName() + "}", this.provideValue(targetPlayer));
    }

    String getName();

    String provideValue(Player targetPlayer);

}
