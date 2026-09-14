package com.eternalcode.core.feature.deathteleport;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@Controller
class DeathTeleportController implements Listener {

    private final DeathTeleportService deathTeleportService;
    private final DeathTeleportToggleService toggleService;
    private final DeathTeleportSettings settings;
    private final TeleportService teleportService;
    private final NoticeService noticeService;
    private final MinecraftScheduler scheduler;

    @Inject
    DeathTeleportController(
        DeathTeleportService deathTeleportService,
        DeathTeleportToggleService toggleService,
        DeathTeleportSettings settings,
        TeleportService teleportService,
        NoticeService noticeService,
        MinecraftScheduler scheduler
    ) {
        this.deathTeleportService = deathTeleportService;
        this.toggleService = toggleService;
        this.settings = settings;
        this.teleportService = teleportService;
        this.noticeService = noticeService;
        this.scheduler = scheduler;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        this.deathTeleportService.markDeathLocation(player.getUniqueId(), PositionAdapter.convert(player.getLocation()));
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!this.settings.deathTeleportEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        Optional<Position> deathLocation = this.deathTeleportService.getDeathLocation(playerId);
        if (deathLocation.isEmpty()) {
            return;
        }

        this.toggleService.getState(playerId).thenAccept(state -> {
            if (state == DeathTeleportState.DISABLED) {
                return;
            }

            if (!player.isOnline()) {
                return;
            }

            this.noticeService.create()
                .player(playerId)
                .notice(translation -> translation.deathTeleport().deathTeleportStarted())
                .placeholder("{TIME}", DurationUtil.format(this.settings.deathTeleportDelay(), true))
                .send();

            this.scheduler.runLater(
                () -> {
                    if (!player.isOnline() || player.isDead()) {
                        return;
                    }

                    Optional<Position> currentDeathLocation = this.deathTeleportService.getDeathLocation(playerId);
                    if (currentDeathLocation.isEmpty() || !currentDeathLocation.get().equals(deathLocation.get())) {
                        return;
                    }

                    this.deathTeleportService.clearDeathLocation(playerId);
                    this.teleportService.teleport(player, PositionAdapter.convert(deathLocation.get()));

                    this.noticeService.create()
                        .player(playerId)
                        .notice(translation -> translation.deathTeleport().deathTeleportSuccess())
                        .send();
                },
                this.settings.deathTeleportDelay()
            );
        });
    }
}
