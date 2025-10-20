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

@Controller
class PlayerJoinMessageController implements Listener {

    private static final String EMPTY_MESSAGE = null;

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
            event.setJoinMessage(EMPTY_MESSAGE);
            return;
        }

        if (!player.hasPlayedBefore()) {
            this.noticeService.create()
                .noticeOptional(translation -> RandomElementUtil.randomElement(translation.join().playerJoinedServerFirstTime()))
                .placeholder("{PLAYER}", player.getName())
                .onlinePlayers()
                .send();
        }

        event.setJoinMessage(EMPTY_MESSAGE);

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.join().playerJoinedServer()))
            .placeholder("{PLAYER}", player.getName())
            .onlinePlayers()
            .sendAsync();
    }
}
