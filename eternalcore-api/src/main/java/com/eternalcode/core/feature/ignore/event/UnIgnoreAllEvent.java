package com.eternalcode.core.feature.ignore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UnIgnoreAllEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player by;
    private boolean cancelled;

    public UnIgnoreAllEvent(@NotNull Player by) {
        super(true);
        this.by = by;
    }

    /**
     * @return the player executing the unignore all action.
     */

    public Player getBy() {
        return by;
    }


    /**
     * @return Whether the event is cancelled
     */

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }


    /**
     * @param cancel Whether to cancel the event
     */
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
