package com.eternalcode.core.feature.kit.usage;

import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.kit.claim.KitFirstJoinService;
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
class KitUsageController implements Listener {

    private final KitUsageStore store;
    private final KitFirstJoinService firstJoinService;
    private final MinecraftScheduler scheduler;

    @Inject
    KitUsageController(KitUsageStore store, KitFirstJoinService firstJoinService, MinecraftScheduler scheduler, Server server) {
        this.store = store;
        this.firstJoinService = firstJoinService;
        this.scheduler = scheduler;

        for (Player player : server.getOnlinePlayers()) {
            this.load(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean firstJoin = !player.hasPlayedBefore();

        this.store.load(player.getUniqueId())
            .thenRun(() -> {
                if (firstJoin) {
                    this.scheduler.run(player, () -> this.firstJoinService.giveFirstJoinKits(player));
                }
            })
            .exceptionally(FutureHandler::handleException);
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
