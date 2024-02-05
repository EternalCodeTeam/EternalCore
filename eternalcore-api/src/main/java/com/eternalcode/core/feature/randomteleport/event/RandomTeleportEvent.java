package com.eternalcode.core.feature.randomteleport.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called after a player is teleported to a random location.
 */
public class RandomTeleportEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final Location teleportLocation;
    private boolean cancelled;

    public RandomTeleportEvent(Player player, Location teleportLocation) {
        super(false);

        this.player = player;
        this.teleportLocation = teleportLocation;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Location getTeleportLocation() {
        return this.teleportLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
