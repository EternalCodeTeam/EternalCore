package com.eternalcode.core.feature.chat.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.time.Duration;
import java.util.UUID;

/**
 * Called when one of the server administrators edit slowmode chat using /chat slowmode command.
 */
public class EditSlowModeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled;
    private final Duration pastChatSlowMode;
    private final Duration currentSlowMode;
    private final UUID playerUniqueId;

    public EditSlowModeEvent(
        Duration pastChatSlowMode,
        Duration currentSlowMode,
        UUID playerUniqueId
    ) {
        super(false);

        this.pastChatSlowMode = pastChatSlowMode;
        this.currentSlowMode = currentSlowMode;
        this.playerUniqueId = playerUniqueId;
    }

    public Duration getPastChatSlowMode() {
        return this.pastChatSlowMode;
    }

    public Duration getCurrentSlowMode() {
        return this.currentSlowMode;
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

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
