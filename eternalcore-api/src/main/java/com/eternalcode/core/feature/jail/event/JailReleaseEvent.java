package com.eternalcode.core.feature.jail.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JailReleaseEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final boolean isInJail;
    private final UUID uuid;
    private boolean isCancelled = false;

    public JailReleaseEvent(@NotNull UUID uniqueId) {
        this.uuid = uniqueId;
        this.isInJail = false;
    }

    public UUID getPlayerUniqueId() {
        return this.uuid;
    }

    public boolean isInJail() {
        return this.isInJail;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
