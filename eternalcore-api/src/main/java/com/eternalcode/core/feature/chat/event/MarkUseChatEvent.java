package com.eternalcode.core.feature.chat.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.time.Duration;
import java.util.UUID;

public class MarkUseChatEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled;
    private Duration slowdomeDuration;
    private final UUID playerUniqueId;

    public MarkUseChatEvent(Duration slowdomeDuration, UUID playerUniqueId) {
        super(false);
        this.slowdomeDuration = slowdomeDuration;
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public Duration getSlowdomeDuration() {
        return this.slowdomeDuration;
    }

    public void setSlowdomeDuration(Duration slowdomeDuration) {
        this.slowdomeDuration = slowdomeDuration;
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
