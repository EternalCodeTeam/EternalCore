package com.eternalcode.core.feature.home.event;

import com.eternalcode.core.feature.home.Home;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a home is overridden with new location.
 */
public class HomeOverrideEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID playerUniqueId;
    private final Home home;
    private Location location;
    private boolean cancelled;

    public HomeOverrideEvent(UUID playerUniqueId, Home home, Location location) {
        super(false);

        this.playerUniqueId = playerUniqueId;
        this.home = home;
        this.location = this.home.getLocation();
    }

    public Home getHome() {
        return home;
    }

    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
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
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
