package com.eternalcode.core.listener.player;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panda.utilities.StringUtils;

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
            this.noticeService.notice()
                .notice(NoticeType.CHAT, messages -> this.config.eventMessage.firstJoinMessage) // TODO: Move to messages config
                .placeholder("{PLAYER}", player.getName())
                .all()
                .send();
        }

        if (this.config.otherSettings.gamemodeOnJoin) {
            if (player.hasPermission("eternalcore.staff.gamemodejoin")) {
                player.setGameMode(GameMode.CREATIVE);
            }
        }

        if (this.config.sound.enabledAfterJoin) {
            this.server.getOnlinePlayers()
                .forEach(online -> online.playSound(online.getLocation(), this.config.sound.afterJoin, this.config.sound.afterJoinVolume, this.config.sound.afterJoinPitch));
        }

        if (this.config.eventMessage.enableWelcomeTitle) {
            this.noticeService
                .notice()
                .notice(NoticeType.TITLE, messages -> this.config.eventMessage.welcomeTitle) //TODO: Move to messages config
                .notice(NoticeType.SUBTITLE, messages -> this.config.eventMessage.welcomeSubTitle) //TODO: Move to messages config
                .placeholder("{PLAYER}", player.getName())
                .player(player.getUniqueId())
                .send();
        }

        event.setJoinMessage(StringUtils.EMPTY);
        this.noticeService.notice()
            .notice(NoticeType.CHAT, messages -> this.config.eventMessage.joinMessage) // TODO: Move to messages config
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();
    }
}
