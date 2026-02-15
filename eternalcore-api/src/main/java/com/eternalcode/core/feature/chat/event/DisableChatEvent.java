package com.eternalcode.core.feature.chat.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * Called when one of the server administrators disables chat using /chat off command.
 */
public class DisableChatEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender commandSender;
    private boolean cancelled;

    public DisableChatEvent(CommandSender commandSender) {
        super(false);
        this.commandSender = commandSender;
    }

    public CommandSender getCommandSender() {
        return this.commandSender;
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
    public @NonNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
