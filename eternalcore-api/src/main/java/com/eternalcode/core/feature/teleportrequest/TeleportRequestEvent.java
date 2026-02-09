package com.eternalcode.core.feature.teleportrequest;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called after a teleport request has been successfully created (via /tpa or
 * /tpahere).
 * This event is informational and cannot be cancelled.
 */
public class TeleportRequestEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player sender;
    private final Player target;

    public TeleportRequestEvent(Player sender, Player target) {
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
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
