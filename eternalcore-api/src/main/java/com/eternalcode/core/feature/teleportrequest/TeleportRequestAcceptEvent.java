package com.eternalcode.core.feature.teleportrequest;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called before accepting a teleport request, before the teleport delay starts.
 * Cancelling this event preserves the request unless {@link #removeRequest()} is explicitly called.
 */
public class TeleportRequestAcceptEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player requester;
    private final Player recipient;
    private final TeleportRequestType requestType;
    private final Runnable requestRemoval;
    private Location destination;
    private boolean cancelled;

    public TeleportRequestAcceptEvent(
        Player requester,
        Player recipient,
        TeleportRequestType requestType,
        Location destination,
        Runnable requestRemoval
    ) {
        super(requester);

        this.requester = requester;
        this.recipient = recipient;
        this.requestType = requestType;
        this.destination = destination;
        this.requestRemoval = requestRemoval;
    }

    public Player getRequester() {
        return this.requester;
    }

    public Player getRecipient() {
        return this.recipient;
    }

    public Player getTeleportingPlayer() {
        return this.requestType == TeleportRequestType.TPA ? this.requester : this.recipient;
    }

    public TeleportRequestType getRequestType() {
        return this.requestType;
    }

    /**
     * Explicitly removes this request, even when the event is canceled.
     * Call this method synchronously from the event handler when replacing the native teleport.
     */
    public void removeRequest() {
        this.requestRemoval.run();
    }

    public Location getDestination() {
        return this.destination;
    }

    public void setDestination(Location destination) {
        Preconditions.checkNotNull(destination, "Destination cannot be null");
        this.destination = destination;
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
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
