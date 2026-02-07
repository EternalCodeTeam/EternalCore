package com.eternalcode.core.feature.msg;

import java.util.UUID;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NullMarked;

/**
 * Event that is called when a player sends a private message to another player.
 */
@NullMarked
public class MsgEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID sender;
    private final UUID receiver;
    private String content;

    public MsgEvent(UUID sender, UUID receiver, String content) {
        super(true);

        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public UUID getSender() {
        return this.sender;
    }

    public UUID getReceiver() {
        return this.receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
