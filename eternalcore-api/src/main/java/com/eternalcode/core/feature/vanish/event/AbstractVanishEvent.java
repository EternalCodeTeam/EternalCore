package com.eternalcode.core.feature.vanish.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

public abstract class AbstractVanishEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;

    public AbstractVanishEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
