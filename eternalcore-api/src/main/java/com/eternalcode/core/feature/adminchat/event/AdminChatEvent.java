package com.eternalcode.core.feature.adminchat.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

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

    public AdminChatEvent(@NotNull CommandSender sender, @NotNull String content) {
        super(false);

        if (sender == null) {
            throw new IllegalArgumentException("Sender cannot be null");
        }
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }

        this.sender = sender;
        this.content = content;
        this.cancelled = false;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @NotNull
    public CommandSender getSender() {
        return this.sender;
    }

    @NotNull
    public String getContent() {
        return this.content;
    }

    public void setContent(@NotNull String content) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
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
    @NotNull
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
