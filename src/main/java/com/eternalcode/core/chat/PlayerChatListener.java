/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.chat;

import com.eternalcode.core.EternalCore;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    private final EternalCore eternalCore;

    public PlayerChatListener(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        this.eternalCore.getChatManager().useChat(event);
    }
}
