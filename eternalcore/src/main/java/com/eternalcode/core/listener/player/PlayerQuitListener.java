package com.eternalcode.core.listener.player;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.util.RandomUtil;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import panda.std.Option;
import panda.utilities.StringUtils;

public class PlayerQuitListener implements Listener {
    private final PluginConfiguration config;
    private final NoticeService noticeService;
    private final Server server;

    public PlayerQuitListener(ConfigurationManager configurationManager, NoticeService noticeService, Server server) {
        this.config = configurationManager.getPluginConfiguration();
        this.noticeService = noticeService;
        this.server = server;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(StringUtils.EMPTY);
        Option<String> message = RandomUtil.randomElement(this.config.eventMessage.leaveMessage);

        if (message.isPresent()) {
            this.noticeService.create()
                .notice(NoticeType.CHAT, messages -> message.get()) // TODO: Move to messages config
                .placeholder("{PLAYER}", player.getName())
                .all()
                .send();
        }

        if (this.config.sound.enableAfterQuit) {
            this.server.getOnlinePlayers()
                .forEach(online -> online.playSound(online.getLocation(), this.config.sound.afterQuit, this.config.sound.afterQuitVolume, this.config.sound.afterQuitPitch));
        }
    }
}
