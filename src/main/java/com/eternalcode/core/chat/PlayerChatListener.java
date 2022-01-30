/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.chat;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.DateUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerChatListener implements Listener {

    private final ChatManager chatManager;
    private final ConfigurationManager configurationManager;

    public PlayerChatListener(ChatManager chatManager, ConfigurationManager configurationManager) {
        this.chatManager = chatManager;
        this.configurationManager = configurationManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        MessagesConfiguration messages = this.configurationManager.getMessagesConfiguration();
        Player player = event.getPlayer();

        if (!this.chatManager.isChatEnabled() && !player.hasPermission("enernalcore.chat.bypass")){
            player.sendMessage(ChatUtils.color(messages.chatDisable));
            event.setCancelled(true);
            return;
        }

        UUID uuid = player.getUniqueId();

        if (this.chatManager.isSlowedOnChat(uuid) && !player.hasPermission("enernalcore.chat.noslowmode")) {
            long time = this.chatManager.getSlowDown(uuid);

            player.sendMessage(ChatUtils.color(messages.chatSlowMode.replace("{TIME}", DateUtils.durationToString(time))));
            event.setCancelled(true);
            return;
        }

        this.chatManager.useChat(uuid);
    }
}
