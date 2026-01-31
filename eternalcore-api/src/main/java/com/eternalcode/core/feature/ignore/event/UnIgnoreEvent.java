package com.eternalcode.core.feature.ignore.event;

import java.util.UUID;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * This event is called when a player unignores another player.
 */
public class UnIgnoreEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID requester;
    private final UUID target;
    private boolean cancelled;

    public UnIgnoreEvent(UUID requester, UUID target) {
        super(false);
        this.requester = requester;
        this.target = target;
    }

    /**
     * @return the player executing the unignore action.
     */
    public UUID getRequester() {
        return this.requester;
    }

    /**
     * @return the player being targeted by the unignore action.
     */
    public UUID getTarget() {
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

    @NonNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
