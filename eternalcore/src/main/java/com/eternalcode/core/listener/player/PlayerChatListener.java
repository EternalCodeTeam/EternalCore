package com.eternalcode.core.listener.player;

import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.util.DurationUtil;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.Duration;
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChatSlowMode(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!this.chatManager.getChatSettings().isChatEnabled() && !player.hasPermission("enernalcore.chat.bypass")) {
            this.noticeService.player(player.getUniqueId(), messages -> messages.chat().disabledChatInfo());
            event.setCancelled(true);
            return;
        }

        UUID uuid = player.getUniqueId();

        if (this.chatManager.hasSlowedChat(uuid) && !player.hasPermission("enernalcore.chat.noslowmode")) {
            Duration time = this.chatManager.getSlowDown(uuid);

            this.noticeService
                .create()
                .player(player.getUniqueId())
                .message(messages -> messages.chat().slowMode())
                .placeholder("{TIME}", DurationUtil.format(time))
                .send();

            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void markUseChat(AsyncPlayerChatEvent event) {
        this.chatManager.markUseChat(event.getPlayer().getUniqueId());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void playSound(AsyncPlayerChatEvent event) {
        PluginConfiguration.Sounds sound = this.config.sound;

        if (!sound.enableAfterChatMessage) {
            return;
        }

        for (Player online : this.server.getOnlinePlayers()) {
            online.playSound(online.getLocation(), sound.afterChatMessage, sound.afterChatMessageVolume, sound.afterChatMessagePitch);
        }
    }

}
