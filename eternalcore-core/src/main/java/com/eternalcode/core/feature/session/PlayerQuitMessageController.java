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
import org.bukkit.event.player.PlayerQuitEvent;
import panda.utilities.StringUtils;

@Controller
class PlayerQuitMessageController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;
    private final PluginConfiguration config;
    private final Server server;

    @Inject
    PlayerQuitMessageController(NoticeService noticeService, VanishService vanishService, PluginConfiguration config, Server server) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
        this.config = config;
        this.server = server;
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.vanishService.isVanished(player)) {
            event.setQuitMessage(StringUtils.EMPTY);
            return;
        }

        event.setQuitMessage(StringUtils.EMPTY);
        PluginConfiguration.Sounds sound = this.config.sound;

        if (sound.enableAfterQuit) {
            for (Player online : this.server.getOnlinePlayers()) {
                online.playSound(online.getLocation(), sound.afterQuit, sound.afterQuitVolume, sound.afterQuitPitch);
            }
        }

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().quitMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();
    }
}
