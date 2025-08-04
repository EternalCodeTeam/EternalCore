package com.eternalcode.core.feature.vanish.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class EnableVanishEvent extends AbstractVanishEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public EnableVanishEvent(Player player) {
        super(player);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
