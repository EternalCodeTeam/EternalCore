package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishConfiguration;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

@Controller
class BlockController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;
    private final VanishConfiguration config;

    @Inject
    BlockController(NoticeService noticeService, VanishService vanishService, VanishConfiguration config) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
        this.config = config;
    }

    @EventHandler
    void onBreak(BlockBreakEvent event) {
        if (!this.config.blockBlockBreaking) {
            return;
        }

        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);

        this.noticeService.player(player.getUniqueId(), message -> message.vanish().cantBlockBreakWhileVanished());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    void onPlace(BlockPlaceEvent event) {
        if (!this.config.blockBlockPlacing) {
            return;
        }

        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);

        this.noticeService.player(player.getUniqueId(), message -> message.vanish().cantBlockPlaceWhileVanished());
    }
}
