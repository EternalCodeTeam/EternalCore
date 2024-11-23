package com.eternalcode.core.feature.randomteleport;

import static com.eternalcode.core.feature.randomteleport.RandomTeleportPermissionConstant.*;
import static com.eternalcode.core.feature.randomteleport.RandomTeleportPlaceholders.*;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.randomteleport.event.PreRandomTeleportEvent;
import com.eternalcode.core.feature.randomteleport.event.RandomTeleportEvent;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import io.papermc.lib.PaperLib;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Location;
import org.bukkit.entity.Player;


@Service
class RandomTeleportTaskService {


    private final NoticeService noticeService;
    private final EventCaller eventCaller;
    private final RandomTeleportSettings randomTeleportSettings;
    private final TeleportTaskService teleportTaskService;

    @Inject
    RandomTeleportTaskService(NoticeService noticeService, EventCaller eventCaller,
        RandomTeleportSettings randomTeleportSettings, TeleportTaskService teleportTaskService) {
        this.noticeService = noticeService;
        this.eventCaller = eventCaller;
        this.randomTeleportSettings = randomTeleportSettings;
        this.teleportTaskService = teleportTaskService;
    }

    CompletableFuture<TeleportResult> createTeleport(Player player, Location location) {
        if (player.hasPermission(RTP_BYPASS_PERMISSION)) {
            PreRandomTeleportEvent preRandomTeleportEvent = new PreRandomTeleportEvent(player);

            if (preRandomTeleportEvent.isCancelled()) {
                return CompletableFuture.completedFuture(new TeleportResult(false, player.getLocation()));
            }

            return PaperLib.teleportAsync(player, location).thenApply(success -> {
                TeleportResult teleportResult = new TeleportResult(success, location);

                RandomTeleportEvent event = new RandomTeleportEvent(player, location);
                this.eventCaller.callEvent(event);

                if (success) {
                    this.noticeService.player(
                        player.getUniqueId(),
                        translation -> translation.randomTeleport().teleportedToRandomLocation(),
                        PLACEHOLDERS.toFormatter(player));
                }

                return teleportResult;
            });
        }

        if (this.teleportTaskService.isInTeleport(player.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.teleport().teleportTaskAlreadyExist())
                .player(player.getUniqueId())
                .send();

            return CompletableFuture.completedFuture(new TeleportResult(false, player.getLocation()));
        }

        Location playerLocation = player.getLocation();
        Position playerPosition = PositionAdapter.convert(playerLocation);
        Position destination = PositionAdapter.convert(location);

        Duration time = this.randomTeleportSettings.randomTeleportTime();

        this.teleportTaskService.createTeleport(player.getUniqueId(), playerPosition, destination, time);
        return CompletableFuture.completedFuture(new TeleportResult(true, location));
    }
}
