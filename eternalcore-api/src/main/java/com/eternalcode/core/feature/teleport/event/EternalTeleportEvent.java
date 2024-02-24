package com.eternalcode.core.feature.teleport.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called before an async player teleportation. (This event is not async)
 * Only called when the {@link com.eternalcode.core.feature.teleport.TeleportService} teleports the player, but
 * this event is not called when the player teleports using the vanilla method.
 */
public class EternalTeleportEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList TELEPORT_HANDLER_LIST = new HandlerList();

    private boolean cancelled;
    private Location location;

    public EternalTeleportEvent(Player player, Location location) {
        super(player);
        this.location = location;
    }

    public static HandlerList getTeleportHandlerList() {
        return TELEPORT_HANDLER_LIST;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
        return TELEPORT_HANDLER_LIST;
    }
}
