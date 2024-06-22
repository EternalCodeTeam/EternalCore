package com.eternalcode.core.feature.ignore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnIgnoreEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player by;
    private final Player target;
    private boolean cancelled;

    public UnIgnoreEvent(@NotNull Player by, @Nullable Player target) {
        super(false);
        this.by = by;
        this.target = target;
    }

    /**
     * @return the player executing the unignore action.
     */

    public Player getBy() {
        return this.by;
    }

    /**
     * @return the player being targeted by the unignore action.
     */

    public Player getTarget() {
        return this.target;
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
