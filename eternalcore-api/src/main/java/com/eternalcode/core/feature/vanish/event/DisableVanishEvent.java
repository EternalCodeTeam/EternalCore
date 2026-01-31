package com.eternalcode.core.feature.vanish.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class DisableVanishEvent extends AbstractVanishEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public DisableVanishEvent(Player player) {
        super(player);
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
