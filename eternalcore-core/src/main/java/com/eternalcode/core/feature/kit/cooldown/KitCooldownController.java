package com.eternalcode.core.feature.kit.cooldown;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Controller
class KitCooldownController implements Listener {

    private final KitCooldownStore store;

    @Inject
    KitCooldownController(KitCooldownStore store, Server server) {
        this.store = store;

        for (Player player : server.getOnlinePlayers()) {
            this.load(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onJoin(PlayerJoinEvent event) {
        this.load(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onQuit(PlayerQuitEvent event) {
        this.store.unload(event.getPlayer().getUniqueId());
    }

    private void load(UUID playerUniqueId) {
        this.store.load(playerUniqueId)
            .exceptionally(FutureHandler::handleException);
    }
}
