package com.eternalcode.core.feature.warp.event;

import com.eternalcode.core.feature.warp.Warp;
import com.google.common.base.Preconditions;
import java.time.Duration;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Called before teleportation to warp.
 */
public class PreWarpTeleportEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private Warp warp;
    private boolean cancelled;
    private Duration teleportTime;
    private @Nullable Location destination;

    public PreWarpTeleportEvent(Player player, Warp warp, Duration teleportTime) {
        super(false);

        this.player = player;
        this.warp = warp;
        this.teleportTime = teleportTime;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Warp getWarp() {
        return this.warp;
    }

    public void setWarp(Warp warp) {
        Preconditions.checkNotNull(warp, "Warp cannot be null");
        this.warp = warp;
    }

    public Duration getTeleportTime() {
        return this.teleportTime;
    }

    public void setTeleportTime(Duration teleportTime) {
        Preconditions.checkNotNull(teleportTime, "Teleport time cannot be null");
        this.teleportTime = teleportTime;
    }

    public Location getDestination() {
        return this.destination != null ? this.destination : this.warp.getLocation();
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
