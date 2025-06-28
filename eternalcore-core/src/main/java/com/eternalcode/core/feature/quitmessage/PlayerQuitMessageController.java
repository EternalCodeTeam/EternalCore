package com.eternalcode.core.feature.quitmessage;


import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import panda.utilities.StringUtils;

@Controller
class PlayerQuitMessageController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;

    @Inject
    PlayerQuitMessageController(NoticeService noticeService, VanishService vanishService) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.vanishService.isVanished(player)) {
            event.setQuitMessage(StringUtils.EMPTY);
            return;
        }

        event.setQuitMessage(StringUtils.EMPTY);

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().quitMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();
    }
}
