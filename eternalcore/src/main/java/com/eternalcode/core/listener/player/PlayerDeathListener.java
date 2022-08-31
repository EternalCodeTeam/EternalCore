package com.eternalcode.core.listener.player;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import panda.utilities.StringUtils;

public class PlayerDeathListener implements Listener {

    private final NoticeService noticeService;
    private final PluginConfiguration config;

    public PlayerDeathListener(NoticeService noticeService, PluginConfiguration config) {
        this.noticeService = noticeService;
        this.config = config;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDeathMessage(StringUtils.EMPTY);

        if (this.config.eventMessage.deathMessage.isEmpty()) {
            return;
        }

        this.noticeService.create()
            .notice(NoticeType.CHAT, messages -> this.config.eventMessage.deathMessage)
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();
    }
}
