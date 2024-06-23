package com.eternalcode.core.feature.ignore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called when a player wants to ignore all players.
 */

public class IgnoreAllEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player by;
    private boolean cancelled;

    public IgnoreAllEvent(@NotNull Player by) {
        super(false);
        this.by = by;
    }

    /**
     * @return the player executing the ignore all action.
     */

    public Player getBy() {
        return this.by;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
