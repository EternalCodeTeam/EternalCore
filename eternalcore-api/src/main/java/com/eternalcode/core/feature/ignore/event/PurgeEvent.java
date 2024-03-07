package com.eternalcode.core.feature.ignore.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PurgeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender by;
    private boolean cancelled;

    public PurgeEvent(@NotNull CommandSender by) {
        super(true);
        this.by = by;
    }

    /**
     * @return the {@link CommandSender} executing the purge action. This could be either a {@link org.bukkit.entity.Player} or a {@link org.bukkit.command.ConsoleCommandSender}.
     */

    public CommandSender getBy() {
        return by;
    }


    /**
     * @return Whether the event is cancelled
     */

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }


    /**
     * @param cancel Whether to cancel the event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
