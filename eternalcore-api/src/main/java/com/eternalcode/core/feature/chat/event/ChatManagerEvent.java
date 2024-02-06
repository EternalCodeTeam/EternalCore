package com.eternalcode.core.feature.chat.event;

import com.eternalcode.core.feature.chat.ChatSettings;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChatManagerEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ChatSettings chatSettings;

    public ChatManagerEvent(ChatSettings chatSettings) {
        super(false);

        this.chatSettings = chatSettings;
    }

    public ChatSettings getChatSettings() {
        return this.chatSettings;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
