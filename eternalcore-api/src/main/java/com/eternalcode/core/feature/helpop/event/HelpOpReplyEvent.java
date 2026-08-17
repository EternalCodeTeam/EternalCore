package com.eternalcode.core.feature.helpop.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event called when an administrator replies to a player's helpop request.
 *
 * <p>This event is triggered before the reply message is actually sent to the player.
 * Plugins can cancel this event to prevent the reply from being sent, or modify
 * the reply content before it's delivered.
 */
public class HelpOpReplyEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player admin;
    private final Player target;
    private String content;
    private boolean cancelled;

    public HelpOpReplyEvent(@NotNull Player admin, @NotNull Player target, @NotNull String content) {
        super(false);

        this.admin = admin;
        this.target = target;
        this.content = content;
    }

    /**
     * Gets the administrator who is replying.
     *
     * @return the admin player
     */
    @NotNull
    public Player getAdmin() {
        return this.admin;
    }

    /**
     * Gets the player who originally sent the helpop request.
     *
     * @return the target player
     */
    @NotNull
    public Player getTarget() {
        return this.target;
    }

    /**
     * Gets the reply message content.
     *
     * @return the reply content
     */
    @NotNull
    public String getContent() {
        return this.content;
    }

    /**
     * Sets the reply message content.
     *
     * @param content the new reply content
     */
    public void setContent(@NotNull String content) {
        this.content = content;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
