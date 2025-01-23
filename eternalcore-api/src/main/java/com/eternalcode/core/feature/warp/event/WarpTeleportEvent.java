package com.eternalcode.core.feature.warp.event;

import com.eternalcode.core.feature.warp.Warp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called after teleportation to warp
 */
public class WarpTeleportEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final Warp warp;
    private final Location destination;

    public WarpTeleportEvent(Player player, Warp warp, Location destination) {
        super(false);

        this.player = player;
        this.warp = warp;
        this.destination = destination;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Warp getWarp() {
        return this.warp;
    }

    public Location getDestination() {
        return this.destination;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
