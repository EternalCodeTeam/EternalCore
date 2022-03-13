package com.eternalcode.core.listener.player;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PluginConfiguration config;
    private final NoticeService noticeService;
    private final Server server;

    public PlayerJoinListener(ConfigurationManager configurationManager, NoticeService noticeService, Server server) {
        this.config = configurationManager.getPluginConfiguration();
        this.noticeService = noticeService;
        this.server = server;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            event.joinMessage(ChatUtils.component(config.eventMessage.firstJoinMessage.replace("{PLAYER}", event.getPlayer().getName())));
        }

        if (config.sound.enabledAfterJoin) {
            this.server.getOnlinePlayers()
                .forEach(online -> online.playSound(online.getLocation(), config.sound.afterJoin, config.sound.afterJoinVolume, config.sound.afterJoinPitch));
        }

        if (config.eventMessage.enableWelcomeTitle){
            this.noticeService
                .notice()
                .notice(NoticeType.TITLE, messages -> config.eventMessage.welcomeTitle) //TODO: Move to messages config
                .notice(NoticeType.SUBTITLE, messages -> config.eventMessage.welcomeSubTitle) //TODO: Move to messages config
                .placeholder("{PLAYER}", player.getName())
                .player(player.getUniqueId())
                .send();
        }

        event.joinMessage(ChatUtils.component(config.eventMessage.joinMessage.replace("{PLAYER}", player.getName())));
    }
}
