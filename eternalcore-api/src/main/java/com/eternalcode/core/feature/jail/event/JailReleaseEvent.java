package com.eternalcode.core.feature.jail.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JailReleaseEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final UUID uuid;
    private boolean cancelled = false;

    public JailReleaseEvent(@NotNull UUID uniqueId) {
        this.uuid = uniqueId;
    }

    public UUID getPlayerUniqueId() {
        return this.uuid;
    }

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
