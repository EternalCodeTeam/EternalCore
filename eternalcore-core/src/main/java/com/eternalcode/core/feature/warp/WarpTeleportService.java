package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.teleport.Teleport;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.feature.warp.event.PreWarpTeleportEvent;
import com.eternalcode.core.feature.warp.event.WarpTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

@Service
@PermissionDocs(name = "Warp bypass", description = "Allows player to bypass warp teleport time.", permission = WarpTeleportService.WARP_BYPASS)
public class WarpTeleportService {

    static final String WARP_BYPASS = "eternalcore.warp.bypass";

    private final TeleportTaskService teleportTaskService;
    private final WarpSettings warpSettings;
    private final EventCaller eventCaller;

    @Inject
    public WarpTeleportService(
            TeleportTaskService teleportTaskService,
            WarpSettings warpSettings,
            EventCaller eventCaller) {
        this.teleportTaskService = teleportTaskService;
        this.warpSettings = warpSettings;
        this.eventCaller = eventCaller;
    }

    public void teleport(Player player, Warp warp) {
        Duration teleportTime = this.calculateTeleportTime(player);

        PreWarpTeleportEvent event = this.eventCaller.callEvent(new PreWarpTeleportEvent(player, warp, teleportTime));

        if (event.isCancelled()) {
            return;
        }

        this.processTeleport(player, event.getWarp(), event.getDestination(), event.getTeleportTime());
    }

    private Duration calculateTeleportTime(Player player) {
        if (player.hasPermission(WARP_BYPASS)) {
            return Duration.ZERO;
        }

        return this.warpSettings.teleportTimeToWarp();
    }

    private void processTeleport(Player player, Warp warp, Location destination, Duration duration) {
        Position destinationPosition = PositionAdapter.convert(destination);
        Position playerPosition = PositionAdapter.convert(player.getLocation());
        UUID uniqueId = player.getUniqueId();

        Teleport teleport = this.teleportTaskService.createTeleport(
                uniqueId,
                playerPosition,
                destinationPosition,
                duration);

        teleport.getResult().whenComplete((result, throwable) -> {
            if (throwable == null) {
                this.eventCaller.callEvent(new WarpTeleportEvent(player, warp, destination));
            }
        });
    }
}
