package com.eternalcode.core.feature.joinquitmessage;


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
class QuitMessageController implements Listener {

    private final NoticeService noticeService;

    @Inject
    QuitMessageController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(StringUtils.EMPTY);

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.joinQuit().quitMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();
    }
}
