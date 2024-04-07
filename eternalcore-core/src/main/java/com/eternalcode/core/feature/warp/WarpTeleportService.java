package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.teleport.Teleport;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.feature.warp.event.PreWarpTeleportEvent;
import com.eternalcode.core.feature.warp.event.WarpTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.entity.Player;

@Service
public class WarpTeleportService {

    private static final String WARP_BYPASS = "eternalcore.warp.bypass";

    private final TeleportTaskService teleportTaskService;
    private final PluginConfiguration pluginConfiguration;
    private final EventCaller eventCaller;

    @Inject
    public WarpTeleportService(
        TeleportTaskService teleportTaskService,
        PluginConfiguration pluginConfiguration,
        EventCaller eventCaller
    ) {
        this.teleportTaskService = teleportTaskService;
        this.pluginConfiguration = pluginConfiguration;
        this.eventCaller = eventCaller;
    }

    public void teleport(Player player, Warp warp) {
        PreWarpTeleportEvent pre = this.eventCaller.callEvent(new PreWarpTeleportEvent(player, warp));

        if (pre.isCancelled()) {
            return;
        }

        Duration teleportTime = player.hasPermission(WARP_BYPASS)
            ? Duration.ZERO
            : this.pluginConfiguration.warp.teleportTimeToWarp;

        Warp destinationWarp = pre.getWarp();
        Position playerLocation = PositionAdapter.convert(player.getLocation());
        Position warpLocation = PositionAdapter.convert(destinationWarp.getLocation());
        UUID uniqueId = player.getUniqueId();

        WarpTeleportEvent post = new WarpTeleportEvent(player, destinationWarp);

        Teleport teleport = this.teleportTaskService.createTeleport(
            uniqueId,
            playerLocation,
            warpLocation,
            teleportTime
        );
        teleport.getResult().whenComplete((result, throwable) -> this.eventCaller.callEvent(post));
    }
}
