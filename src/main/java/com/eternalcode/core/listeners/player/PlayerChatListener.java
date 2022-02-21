package com.eternalcode.core.listeners.player;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.utils.DateUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerChatListener implements Listener {

    private final ChatManager chatManager;
    private final MessagesConfiguration message;
    private final PluginConfiguration config;
    private final Server server;

    public PlayerChatListener(ChatManager chatManager, ConfigurationManager configurationManager, Server server) {
        this.chatManager = chatManager;
        this.message = configurationManager.getMessagesConfiguration();
        this.config = configurationManager.getPluginConfiguration();
        this.server = server;
    }

    @EventHandler
    public void onChatSlowmode(AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (!this.chatManager.isChatEnabled() && !player.hasPermission("enernalcore.chat.bypass")) {
            player.sendMessage(ChatUtils.color(message.chatSection.disable));
            event.setCancelled(true);
            return;
        }

        UUID uuid = player.getUniqueId();

        if (this.chatManager.isSlowedOnChat(uuid) && !player.hasPermission("enernalcore.chat.noslowmode")) {
            long time = this.chatManager.getSlowDown(uuid);

            player.sendMessage(ChatUtils.color(message.chatSection.slowMode).replace("{TIME}", DateUtils.durationToString(time)));
            event.setCancelled(true);
            return;
        }

        if (this.config.sound.enableAfterChatMessage){
            this.server.getOnlinePlayers()
                .forEach(online -> online.playSound(online.getLocation(),
                    this.config.sound.afterChatMessage,
                    this.config.sound.afterChatMessageVolume,
                    this.config.sound.afterChatMessagePitch)
                );
        }

        this.chatManager.useChat(uuid);
    }
}
