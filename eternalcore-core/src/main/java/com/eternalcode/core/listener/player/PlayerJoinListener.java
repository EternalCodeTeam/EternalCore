package com.eternalcode.core.listener.player;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.RandomUtil;
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

    @FeatureDocs(
        description = "Send a welcome message to a player when they join the server",
        name = "Player Join Message"
    )
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

        this.noticeService.create()
            .notice(translation -> translation.event().welcomeTitle())
            .notice(translation -> translation.event().welcomeSubtitle())
            .placeholder("{PLAYER}", player.getName())
            .player(player.getUniqueId())
            .sendAsync();

        event.setJoinMessage(StringUtils.EMPTY);

        this.noticeService.create()
            .noticeOption(translation -> RandomUtil.randomElement(translation.event().joinMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .sendAsync();

    }

    @FeatureDocs(
        description = "Play a sound after a player joins the server",
        name = "Player Join Sound"
    )
    @EventHandler
    public void onPlayerJoinSound(PlayerJoinEvent event) {
        if (this.config.sound.enabledAfterJoin) {
            for (Player online : this.server.getOnlinePlayers()) {
                online.playSound(online.getLocation(), this.config.sound.afterJoin, this.config.sound.afterJoinVolume, this.config.sound.afterJoinPitch);
            }
        }
    }
}
