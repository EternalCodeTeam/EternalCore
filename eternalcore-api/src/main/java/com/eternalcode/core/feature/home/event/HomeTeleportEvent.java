package com.eternalcode.core.feature.home.event;

import com.eternalcode.core.feature.home.Home;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Called after teleportation to home.
 */
public class HomeTeleportEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID playerUniqueId;
    private final Home home;

    public HomeTeleportEvent(UUID playerUniqueId, Home home) {
        super(false);

        this.playerUniqueId = playerUniqueId;
        this.home = home;
    }

    public Home getHome() {
        return this.home;
    }

    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
