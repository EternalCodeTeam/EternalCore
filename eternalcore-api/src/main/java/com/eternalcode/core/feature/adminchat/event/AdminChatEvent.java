package com.eternalcode.core.feature.adminchat.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * Event called when an admin chat message is being sent.
 *
 * <p>This event is triggered before the message is actually sent to all recipients.
 * Plugins can cancel this event to prevent the message from being sent, or modify
 * the message content before it's delivered.
 */
public class AdminChatEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender sender;
    private String content;
    private boolean cancelled;

    public AdminChatEvent(CommandSender sender, String content) {
        super(false);

        this.sender = java.util.Objects.requireNonNull(sender, "sender cannot be null");
        this.content = java.util.Objects.requireNonNull(content, "content cannot be null");
        this.cancelled = false;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
