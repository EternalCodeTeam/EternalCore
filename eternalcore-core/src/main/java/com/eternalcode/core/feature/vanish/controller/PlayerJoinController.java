package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishPermissionConstant;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
class PlayerJoinController implements Listener {

    private final VanishService vanishService;
    private final NoticeService noticeService;
    private final VanishSettings settings;

    @Inject
    PlayerJoinController(VanishService vanishService, NoticeService noticeService, VanishSettings settings) {
        this.vanishService = vanishService;
        this.noticeService = noticeService;
        this.settings = settings;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (this.settings.silentJoin() && player.hasPermission(VanishPermissionConstant.VANISH_JOIN_PERMISSION)) {
            event.setJoinMessage(null);
            this.vanishService.enableVanish(player);

            this.noticeService.player(player.getUniqueId(), messages -> messages.vanish().joinedInVanish());

            this.noticeService.create()
                .onlinePlayers(VanishPermissionConstant.VANISH_SEE_PERMISSION)
                .notice(messages -> messages.vanish().playerJoinedInVanish())
                .placeholder("{PLAYER}", player.getName())
                .send();

            return;
        }

        if (player.hasPermission(VanishPermissionConstant.VANISH_SEE_PERMISSION)) {
            return;
        }

        this.vanishService.hideVanishedPlayersFrom(player);
    }
}
