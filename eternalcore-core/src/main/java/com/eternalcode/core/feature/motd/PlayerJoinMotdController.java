package com.eternalcode.core.feature.motd;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
class PlayerJoinMotdController implements Listener {

    private final NoticeService noticeService;
    private final MotdSettings motdSettings;

    @Inject
    PlayerJoinMotdController(NoticeService noticeService, MotdSettings motdSettings) {
        this.noticeService = noticeService;
        this.motdSettings = motdSettings;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.noticeService.create()
            .notice(this.motdSettings.motdContent())
            .player(player.getUniqueId())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{TIME}", String.valueOf(player.getWorld().getTime()))
            .placeholder("{WORLD}", player.getWorld().getName())
            .send();
    }

}
