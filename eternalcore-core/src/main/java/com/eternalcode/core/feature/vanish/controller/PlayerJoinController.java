package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishConfiguration;
import com.eternalcode.core.feature.vanish.VanishPermissionConstant;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
public class PlayerJoinController implements Listener {

    private final VanishService vanishService;
    private final NoticeService noticeService;
    private final VanishConfiguration config;

    @Inject
    public PlayerJoinController(VanishService vanishService, NoticeService noticeService, VanishConfiguration config) {
        this.vanishService = vanishService;
        this.noticeService = noticeService;
        this.config = config;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (this.config.onJoinWithPerm && player.hasPermission(VanishPermissionConstant.VANISH_JOIN_PERMISSION)) {
            event.setJoinMessage(null);
            this.vanishService.enableVanish(player);

            this.noticeService.player(player.getUniqueId(), messages -> messages.vanish().joinedInVanishMode());

            this.noticeService.create()
                .onlinePlayers(VanishPermissionConstant.VANISH_JOIN_PERMISSION)
                .notice(messages -> messages.vanish().playerJoinedInVanishMode())
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
