package com.eternalcode.core.feature.joinmessage;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.shared.RandomElementUtil;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panda.utilities.StringUtils;

@FeatureDocs(
    description = "Send a random welcome message from config to a player when they join the server",
    name = "Player Join Message"
)
@Controller
class PlayerJoinMessageController implements Listener {

    private final NoticeService noticeService;

    @Inject
    PlayerJoinMessageController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            this.noticeService.create()
                .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().firstJoinMessage()))
                .placeholder("{PLAYER}", player.getName())
                .onlinePlayers()
                .send();
        }

        this.noticeService.create()
            .notice(translation -> translation.event().welcome())
            .placeholder("{PLAYER}", player.getName())
            .player(player.getUniqueId())
            .sendAsync();

        event.setJoinMessage(StringUtils.EMPTY);

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().joinMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .sendAsync();
    }
}
