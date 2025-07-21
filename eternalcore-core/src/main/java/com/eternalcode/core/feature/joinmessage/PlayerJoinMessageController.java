package com.eternalcode.core.feature.joinmessage;


import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panda.utilities.StringUtils;

@Controller
class PlayerJoinMessageController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;

    @Inject
    PlayerJoinMessageController(NoticeService noticeService, VanishService vanishService) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
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
