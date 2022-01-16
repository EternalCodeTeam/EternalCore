package com.eternalcode.core.chat;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.DateUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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

        if (!this.chatManager.isChatEnabled() && !player.hasPermission("enernalCore.chat.bypass")){
            player.sendMessage(ChatUtils.color(messages.chatDisable));
            event.setCancelled(true);
            return;
        }

        if (this.chatManager.isSlowedOnChat(player) && !player.hasPermission("enernalCore.chat.noslowmode")) {
            long time = this.chatManager.getSlowDown(player);

            player.sendMessage(ChatUtils.color(messages.chatSlowMode.replace("{TIME}", DateUtils.durationToString(time))));
            event.setCancelled(true);
            return;
        }

        this.chatManager.useChat(player);
    }
}
