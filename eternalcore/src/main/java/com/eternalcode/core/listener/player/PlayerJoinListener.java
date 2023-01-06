package com.eternalcode.core.listener.player;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.RandomUtil;
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

    public PlayerJoinListener(PluginConfiguration config, NoticeService noticeService, Server server) {
        this.config = config;
        this.noticeService = noticeService;
        this.server = server;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            this.noticeService.create()
                .noticeOption(translation -> RandomUtil.randomElement(translation.event().firstJoinMessage()))
                .placeholder("{PLAYER}", player.getName())
                .onlinePlayers()
                .send();
        }

        if (this.config.otherSettings.gameModeOnJoin) {
            if (player.hasPermission("eternalcore.staff.gamemodejoin")) {
                player.setGameMode(GameMode.CREATIVE);
            }
        }

        if (this.config.sound.enabledAfterJoin) {
            this.server.getOnlinePlayers()
                .forEach(online -> online.playSound(online.getLocation(), this.config.sound.afterJoin, this.config.sound.afterJoinVolume, this.config.sound.afterJoinPitch));
        }

        this.noticeService
            .create()
            .notice(translation -> translation.event().welcomeTitle())
            .notice(translation -> translation.event().welcomeSubtitle())
            .placeholder("{PLAYER}", player.getName())
            .player(player.getUniqueId())
            .send();

        event.setJoinMessage(StringUtils.EMPTY);

        this.noticeService.create()
            .noticeOption(translation -> RandomUtil.randomElement(translation.event().joinMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();

    }
}
