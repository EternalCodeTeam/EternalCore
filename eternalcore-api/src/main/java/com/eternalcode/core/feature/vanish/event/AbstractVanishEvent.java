package com.eternalcode.core.feature.vanish.event;

import org.bukkit.event.Event;
import org.bukkit.entity.Player;

public abstract class AbstractVanishEvent extends Event {

    private final Player player;

    public AbstractVanishEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
