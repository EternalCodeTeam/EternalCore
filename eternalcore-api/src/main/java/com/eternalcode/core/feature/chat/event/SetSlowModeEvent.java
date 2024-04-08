package com.eternalcode.core.feature.chat.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.time.Duration;
import java.util.UUID;

public class SetSlowModeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled;
    private Duration oldSlowdomeDuration;
    private Duration newSlowdomeDuration;
    private final UUID playerUniqueId;

    public SetSlowModeEvent(Duration oldSlowdomeDuration, Duration newSlowdomeDuration, UUID playerUniqueId) {
        super(false);

        this.oldSlowdomeDuration = oldSlowdomeDuration;
        this.newSlowdomeDuration = newSlowdomeDuration;
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public Duration getOldSlowdomeDuration() {
        return this.oldSlowdomeDuration;
    }

    public Duration getNewSlowdomeDuration() {
        return this.newSlowdomeDuration;
    }

    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
