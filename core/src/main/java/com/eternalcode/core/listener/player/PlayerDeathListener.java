package com.eternalcode.core.listener.player;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.RandomUtil;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;
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
            .noticeOption(translation -> RandomUtil.randomElement(translation.event().deathMessage()))
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{KILLER}", translation -> translation.event().unknownPlayerDeath())
            .onlinePlayers()
            .send();
    }
}
