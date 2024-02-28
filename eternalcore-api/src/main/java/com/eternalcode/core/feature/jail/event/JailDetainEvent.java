package com.eternalcode.core.feature.jail.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JailDetainEvent extends PlayerEvent {

    private static final HandlerList JAIL_DETAIN_HANDLER_LIST = new HandlerList();
    private final Player detainedBy;
    private final boolean isInJail;

    public JailDetainEvent(@NotNull Player player, Player detainedBy) {
        super(player);
        this.detainedBy = detainedBy;
        this.isInJail = true;
    }

    public Player getDetainedBy() {
        return this.detainedBy;
    }

    /**
     * Checks if the player is in jail.
     */

    public boolean isInJail() {
        return this.isInJail;
    }

    public boolean isCancelled() {
        return false;
    }

    @Override
    public HandlerList getHandlers() {
        return JAIL_DETAIN_HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return JAIL_DETAIN_HANDLER_LIST;
    }
}
