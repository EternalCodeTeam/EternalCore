package com.eternalcode.core.feature.privatechat;

import java.util.UUID;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PrivateChatEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID sender;
    private final UUID receiver;
    private final String content;

    public PrivateChatEvent(UUID sender, UUID receiver, String content) {
        super(false);

        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public UUID getSender() {
        return this.sender;
    }

    public UUID getReceiver() {
        return this.receiver;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
