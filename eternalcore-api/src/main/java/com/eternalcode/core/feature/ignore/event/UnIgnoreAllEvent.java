package com.eternalcode.core.feature.ignore.event;

import java.util.UUID;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * This event is called when a player ignores all other players.
 */
public class UnIgnoreAllEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID requester;
    private boolean cancelled;

    public UnIgnoreAllEvent(UUID requester) {
        super(false);
        this.requester = requester;
    }

    /**
     * @return the player executing the unignore all action.
     */
    public UUID getRequester() {
        return this.requester;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NonNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
