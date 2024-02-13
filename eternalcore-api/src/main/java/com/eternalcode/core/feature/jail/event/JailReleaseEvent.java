package com.eternalcode.core.feature.jail.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JailReleaseEvent extends Event implements Cancellable {

    private static final HandlerList JAIL_RELEASE_HANDLER_LIST = new HandlerList();
    private final boolean isInJail;
    private final UUID player;

    public JailReleaseEvent(@NotNull UUID uniqueId) {
        this.player = uniqueId;
        this.isInJail = false;
    }


    /**
     * Checks if the player is in jail.
     */
    public UUID getPlayer() {
        return this.player;
    }

    public boolean isInJail() {
        return this.isInJail;
    }

    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    @Override
    public HandlerList getHandlers() {
        return JAIL_RELEASE_HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return JAIL_RELEASE_HANDLER_LIST;
    }
}
