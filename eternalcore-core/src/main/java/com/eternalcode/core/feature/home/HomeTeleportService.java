package com.eternalcode.core.feature.home;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.home.event.HomeTeleportEvent;
import com.eternalcode.core.feature.home.event.PreHomeTeleportEvent;
import com.eternalcode.core.feature.teleport.Teleport;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.entity.Player;

@Service
public class HomeTeleportService {

    private final TeleportTaskService teleportTaskService;
    private final PluginConfiguration pluginConfiguration;
    private final EventCaller eventCaller;

    public static final String HOME_BYPASS = "eternalcore.home.bypass";

    @Inject
    public HomeTeleportService(
        TeleportTaskService teleportTaskService,
        PluginConfiguration pluginConfiguration,
        EventCaller eventCaller
    ) {
        this.teleportTaskService = teleportTaskService;
        this.pluginConfiguration = pluginConfiguration;
        this.eventCaller = eventCaller;
    }

    public void teleport(Player player, Home home) {
        UUID uniqueId = player.getUniqueId();

        PreHomeTeleportEvent pre = this.eventCaller.callEvent(new PreHomeTeleportEvent(uniqueId, home));

        if (pre.isCancelled()) {
            return;
        }

        Duration teleportTime = player.hasPermission(HOME_BYPASS)
            ? Duration.ZERO
            : this.pluginConfiguration.homes.teleportTimeToHomes;

        Position playerLocation = PositionAdapter.convert(player.getLocation());
        Position homeLocation = PositionAdapter.convert(pre.getLocation());

        HomeTeleportEvent post = new HomeTeleportEvent(uniqueId, home);
        Teleport teleport = this.teleportTaskService.createTeleport(
            uniqueId,
            playerLocation,
            homeLocation,
            teleportTime
        );

        teleport.getResult().whenComplete((result, throwable) -> this.eventCaller.callEvent(post));
    }
}
