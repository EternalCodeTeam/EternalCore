package com.eternalcode.core.feature.chat.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChatManagerEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public ChatManagerEvent() {
        super(false);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
