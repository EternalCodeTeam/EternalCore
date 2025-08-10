package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

@Controller
class ItemController implements Listener {

    private final NoticeService noticeService;
    private final VanishService vanishService;
    private final VanishSettings settings;

    @Inject
    ItemController(NoticeService noticeService, VanishService vanishService, VanishSettings settings) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
        this.settings = settings;
    }

    @EventHandler
    void onPickUp(EntityPickupItemEvent event) {
        if (!this.settings.blockItemPickup()) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onDrop(PlayerDropItemEvent event) {
        if (!this.settings.blockItemDropping()) {
            return;
        }
        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);

        this.noticeService.player(player.getUniqueId(), message -> message.vanish().cantDropItemsWhileVanished());
    }
}
