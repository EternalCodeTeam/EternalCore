package com.eternalcode.core.listener.player;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.RandomUtil;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import panda.utilities.StringUtils;

public class PlayerQuitListener implements Listener {
    private final PluginConfiguration config;
    private final NoticeService noticeService;
    private final Server server;

    public PlayerQuitListener(PluginConfiguration config, NoticeService noticeService, Server server) {
        this.config = config;
        this.noticeService = noticeService;
        this.server = server;
    }

    @FeatureDocs(
        description = "Send a random goodbye message from config to a player when they quit the server",
        name = "Player Quit Message"
    )
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(StringUtils.EMPTY);

        this.noticeService.create()
            .noticeOption(translation -> RandomUtil.randomElement(translation.event().quitMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();
    }

    @FeatureDocs(
        description = "Play a sound after a player quits the server",
        name = "Player Quit Sound"
    )
    @EventHandler
    public void onPlayerQuitSound(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.config.sound.enableAfterQuit) {
            for (Player online : this.server.getOnlinePlayers()) {
                online.playSound(online.getLocation(), this.config.sound.afterQuit, this.config.sound.afterQuitVolume, this.config.sound.afterQuitPitch);
            }
        }
    }
}
