package com.eternalcode.core.afk;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@Controller
class AfkController implements Listener {

    private final AfkService afkService;

    @Inject
    public AfkController(AfkService afkService) {
        this.afkService = afkService;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onMove(PlayerMoveEvent event) {
        UUID uniqueId = event.getPlayer().getUniqueId();

        this.afkService.markInteraction(uniqueId);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onQuit(PlayerQuitEvent event) {
        this.afkService.clearAfk(event.getPlayer().getUniqueId());
    }

}
