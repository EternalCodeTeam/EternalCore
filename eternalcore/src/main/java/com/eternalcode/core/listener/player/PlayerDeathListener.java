package com.eternalcode.core.listener.player;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.util.RandomUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import panda.utilities.StringUtils;

public class PlayerDeathListener implements Listener {

    private final NoticeService noticeService;

    public PlayerDeathListener(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDeathMessage(StringUtils.EMPTY);

        this.noticeService.create()
            .noticeOption(messages -> RandomUtil.randomElement(messages.eventMessages().deathMessage()))
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();
    }
}
