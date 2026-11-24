package com.eternalcode.core.feature.spawn.event;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called before a spawn command or SpawnService teleport starts its delay or teleportation.
 * Cancelling this event prevents both the delay and the teleport.
 */
public class PreSpawnTeleportEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Location destination;
    private boolean cancelled;

    public PreSpawnTeleportEvent(Player player, Location destination) {
        super(player);

        this.player = player;
        this.destination = destination;
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
