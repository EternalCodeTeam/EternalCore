package com.eternalcode.core.feature.session;


import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panda.utilities.StringUtils;

@Controller
class PlayerJoinMessageController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;
    private final PluginConfiguration config;
    private final Server server;

    @Inject
    PlayerJoinMessageController(NoticeService noticeService, VanishService vanishService, PluginConfiguration config, Server server) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
        this.config = config;
        this.server = server;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (this.vanishService.isVanished(player)) {
            event.setJoinMessage(StringUtils.EMPTY);
            return;
        }

        if (!player.hasPlayedBefore()) {
            this.noticeService.create()
                .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().firstJoinMessage()))
                .placeholder("{PLAYER}", player.getName())
                .onlinePlayers()
                .send();
        }

        event.setJoinMessage(StringUtils.EMPTY);
        PluginConfiguration.Sounds sound = this.config.sound;

        if (sound.enabledAfterJoin) {
            for (Player online : this.server.getOnlinePlayers()) {
                online.playSound(online.getLocation(), sound.afterJoin, sound.afterJoinVolume, sound.afterJoinPitch);
            }
        }

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().joinMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .sendAsync();
    }
}
