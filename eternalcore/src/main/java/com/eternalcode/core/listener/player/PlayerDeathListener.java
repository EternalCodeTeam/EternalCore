package com.eternalcode.core.listener.player;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import panda.utilities.StringUtils;

public class PlayerDeathListener implements Listener {

    private final NoticeService noticeService;
    private final PluginConfiguration config;

    public PlayerDeathListener(NoticeService noticeService, ConfigurationManager configurationManager) {
        this.noticeService = noticeService;
        this.config = configurationManager.getPluginConfiguration();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDeathMessage(StringUtils.EMPTY);

        if (config.eventMessage.deathMessage.isEmpty()) {
            return;
        }

        this.noticeService.notice()
            .notice(NoticeType.CHAT, messages -> config.eventMessage.deathMessage)
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();
    }
}
