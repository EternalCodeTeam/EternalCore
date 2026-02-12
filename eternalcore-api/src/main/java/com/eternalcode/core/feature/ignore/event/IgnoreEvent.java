package com.eternalcode.core.feature.ignore.event;

import java.util.UUID;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * This event is called when a player wants to ignore another player.
 */
public class IgnoreEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID requester;
    private final UUID target;
    private boolean cancelled;

    public IgnoreEvent(UUID requester, UUID target) {
        super(false);
        this.requester = requester;
        this.target = target;
    }

    /**
     * @return the player who requested the ignore action.
     */
    public UUID getRequester() {
        return this.requester;
    }

    /**
     * @return the player who is being ignored.
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

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
