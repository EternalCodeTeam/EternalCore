package com.eternalcode.core.feature.jail.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class JailReleaseEvent extends PlayerEvent {

    private static final HandlerList JAIL_RELEASE_HANDLER_LIST = new HandlerList();
    private final boolean isInJail;

    public JailReleaseEvent(@NotNull Player player) {
        super(player);
        this.isInJail = false;
    }


    /**
     * Checks if the player is in jail.
     */

    public boolean isInJail() {
        return this.isInJail;
    }

    public boolean isCancelled() {
        return false;
    }

    @Override
    public HandlerList getHandlers() {
        return JAIL_RELEASE_HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return JAIL_RELEASE_HANDLER_LIST;
    }
}
