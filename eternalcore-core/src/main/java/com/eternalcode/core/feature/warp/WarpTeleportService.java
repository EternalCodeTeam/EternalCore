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
import java.time.Duration;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
@PermissionDocs(
    name = "Warp bypass",
    description = "Allows player to bypass warp teleport time.",
    permission = WarpTeleportService.WARP_BYPASS
)
public class WarpTeleportService {

    static final String WARP_BYPASS = "eternalcore.warp.bypass";

    private final TeleportTaskService teleportTaskService;
    private final WarpSettings warpSettings;
    private final EventCaller eventCaller;

    @Inject
    public WarpTeleportService(
        TeleportTaskService teleportTaskService,
        WarpSettings warpSettings,
        EventCaller eventCaller
    ) {
        this.teleportTaskService = teleportTaskService;
        this.warpSettings = warpSettings;
        this.eventCaller = eventCaller;
    }

    public void teleport(Player player, Warp warp) {
        Duration teleportTime = player.hasPermission(WARP_BYPASS)
            ? Duration.ZERO
            : this.warpSettings.teleportTimeToWarp();

        PreWarpTeleportEvent pre = this.eventCaller.callEvent(new PreWarpTeleportEvent(player, warp, teleportTime));

        if (pre.isCancelled()) {
            return;
        }

        Warp destinationWarp = pre.getWarp();
        Location destination = pre.getDestination();
        Position destinationLocation = PositionAdapter.convert(destination);
        Position playerLocation = PositionAdapter.convert(player.getLocation());
        UUID uniqueId = player.getUniqueId();

        Teleport teleport = this.teleportTaskService.createTeleport(
            uniqueId,
            playerLocation,
            destinationLocation,
            pre.getTeleportTime()
        );

        teleport.getResult().whenComplete((result, throwable) -> {
            this.eventCaller.callEvent(new WarpTeleportEvent(player, destinationWarp, destination));
        });
    }
}
