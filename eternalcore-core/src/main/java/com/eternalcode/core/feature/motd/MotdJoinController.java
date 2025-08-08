package com.eternalcode.core.feature.motd;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
class MotdJoinController implements Listener {

    private static final Placeholders<Player> PLACEHOLDERS = Placeholders.<Player>builder()
        .with("{PLAYER}", Player::getName)
        .with("{TIME}", player -> String.valueOf(player.getWorld().getTime()))
        .with("{WORLD}", player -> player.getWorld().getName())
        .build();

    private final NoticeService noticeService;
    private final MotdSettings motdSettings;

    @Inject
    MotdJoinController(NoticeService noticeService, MotdSettings motdSettings) {
        this.noticeService = noticeService;
        this.motdSettings = motdSettings;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.noticeService.create()
            .notice(this.motdSettings.motdContent())
            .player(player.getUniqueId())
            .formatter(PLACEHOLDERS.toFormatter(player))
            .send();
    }
}
