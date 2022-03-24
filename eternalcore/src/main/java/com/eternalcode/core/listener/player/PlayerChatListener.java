package com.eternalcode.core.listener.player;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.utils.DateUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerChatListener implements Listener {

    private final ChatManager chatManager;
    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;

    public PlayerChatListener(ChatManager chatManager, NoticeService noticeService, ConfigurationManager configurationManager, Server server) {
        this.chatManager = chatManager;
        this.noticeService = noticeService;
        this.config = configurationManager.getPluginConfiguration();
        this.server = server;
    }

    @EventHandler
    public void onChatSlowmode(AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (!this.chatManager.getChatSettings().isChatEnabled() && !player.hasPermission("enernalcore.chat.bypass")) {
            this.noticeService
                .notice()
                .player(player.getUniqueId())
                .message(messages -> messages.chat().disabledChatInfo())
                .send();

            event.setCancelled(true);
            return;
        }

        UUID uuid = player.getUniqueId();

        if (this.chatManager.hasSlowedChat(uuid) && !player.hasPermission("enernalcore.chat.noslowmode")) {
            long time = this.chatManager.getSlowDown(uuid);

            this.noticeService
                .notice()
                .player(player.getUniqueId())
                .message(messages -> messages.chat().disabledChatInfo())
                .placeholder("{TIME}", DateUtils.durationToString(time))
                .send();

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

        this.chatManager.markUseChat(uuid);
    }
}
