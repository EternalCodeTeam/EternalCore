package com.eternalcode.core.feature.home.event;

import com.eternalcode.core.feature.home.Home;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NullMarked;

/**
 * Called before teleportation to home.
 */
@NullMarked
public class PreHomeTeleportEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID playerUniqueId;
    private final Home home;
    private Location location;
    private boolean cancelled;

    public PreHomeTeleportEvent(UUID playerUniqueId, Home home) {
        super(false);

        this.playerUniqueId = playerUniqueId;
        this.home = home;
        this.location = home.getLocation();
    }

    public Home getHome() {
        return this.home;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
