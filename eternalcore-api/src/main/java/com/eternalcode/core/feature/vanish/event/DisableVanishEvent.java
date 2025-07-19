package com.eternalcode.core.feature.vanish.event;

import org.bukkit.entity.Player;

public class DisableVanishEvent extends AbstractVanishEvent {

    public DisableVanishEvent(Player player) {
        super(player);
    }
}
