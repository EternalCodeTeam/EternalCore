package com.eternalcode.core.feature.quitmessage;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.shared.RandomElementUtil;
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

    @Inject
    PlayerQuitMessageController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @FeatureDocs(
        description = "Send a random goodbye message from config to a player when they quit the server",
        name = "Player Quit Message"
    )
    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(StringUtils.EMPTY);

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().quitMessage()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .send();
    }
}
