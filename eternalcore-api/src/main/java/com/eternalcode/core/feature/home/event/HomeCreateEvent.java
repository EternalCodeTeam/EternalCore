package com.eternalcode.core.feature.home.event;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * Event called when a home is created.
 */
public class HomeCreateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID playerUniqueId;
    private final UUID homeUniqueId;
    private String homeName;
    private Location location;
    private boolean cancelled;

    public HomeCreateEvent(UUID playerUniqueId, String homeName, UUID homeUniqueId, Location location) {
        super(false);
        this.playerUniqueId = playerUniqueId;
        this.homeName = homeName;
        this.homeUniqueId = homeUniqueId;
        this.location = location;
    }

    public UUID getHomeUniqueId() {
        return this.homeUniqueId;
    }

    public String getHomeName() {
        return this.homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
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
    public @NonNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
