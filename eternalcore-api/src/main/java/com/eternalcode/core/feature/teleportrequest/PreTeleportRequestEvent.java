package com.eternalcode.core.feature.teleportrequest;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called before a teleport request is sent (via /tpa or /tpahere).
 * If cancelled, the teleport request will not be created.
 */
public class PreTeleportRequestEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player sender;
    private final Player target;
    private boolean cancelled;

    public PreTeleportRequestEvent(Player sender, Player target) {
        super(false);

        this.sender = sender;
        this.target = target;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public Player getSender() {
        return this.sender;
    }

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

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
