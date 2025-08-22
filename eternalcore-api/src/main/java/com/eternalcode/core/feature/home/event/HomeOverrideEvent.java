package com.eternalcode.core.feature.home.event;

import com.eternalcode.commons.bukkit.position.Position;

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
    private final UUID homeUniqueId;
    private String homeName;
    private Position position;
    private boolean cancelled;

    public HomeOverrideEvent(UUID playerUniqueId, String homeName, UUID homeUniqueId, Position position) {
        super(false);

        this.playerUniqueId = playerUniqueId;
        this.homeName = homeName;
        this.homeUniqueId = homeUniqueId;
        this.position = position;
    }

    public String getHomeName() {
        return this.homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public UUID getHomeUniqueId() {
        return this.homeUniqueId;
    }

    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
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
