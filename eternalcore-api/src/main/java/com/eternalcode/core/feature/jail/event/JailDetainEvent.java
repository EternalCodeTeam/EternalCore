package com.eternalcode.core.feature.jail.event;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class JailDetainEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final CommandSender detainedBy;
    private final boolean isInJail;

    private boolean isCancelled;

    public JailDetainEvent(@NotNull Player player, CommandSender detainedBy) {
        super(player);
        this.detainedBy = detainedBy;
        this.isInJail = true;
        this.isCancelled = false;
    }

    public CommandSender getDetainedBy() {
        return this.detainedBy;
    }

    /**
     * Checks if the player is in jail.
     */
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
