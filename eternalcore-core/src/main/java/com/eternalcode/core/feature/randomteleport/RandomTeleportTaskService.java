package com.eternalcode.core.feature.randomteleport;

import static com.eternalcode.core.feature.randomteleport.RandomTeleportPermissionConstant.RTP_BYPASS_PERMISSION;
import static com.eternalcode.core.feature.randomteleport.RandomTeleportResolveWorldUtil.resolveWorld;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.Teleport;
import com.eternalcode.core.feature.teleport.TeleportResult;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Service
class RandomTeleportTaskService {

    private final NoticeService noticeService;
    private final RandomTeleportSettings randomTeleportSettings;
    private final TeleportTaskService teleportTaskService;
    private final RandomTeleportSafeLocationService randomTeleportSafeLocationService;
    private final RandomTeleportService randomTeleportService;

    @Inject
    RandomTeleportTaskService(
        NoticeService noticeService,
        RandomTeleportSettings randomTeleportSettings,
        TeleportTaskService teleportTaskService,
        RandomTeleportSafeLocationService randomTeleportSafeLocationService, RandomTeleportService randomTeleportService
    ) {
        this.noticeService = noticeService;
        this.randomTeleportSettings = randomTeleportSettings;
        this.teleportTaskService = teleportTaskService;
        this.randomTeleportSafeLocationService = randomTeleportSafeLocationService;
        this.randomTeleportService = randomTeleportService;
    }

    CompletableFuture<RandomTeleportResult> createTeleport(Player player) {
        World world = resolveWorld(player, randomTeleportSettings);
        RandomTeleportRadius radius = this.randomTeleportSettings.randomTeleportRadius();
        return this.randomTeleportSafeLocationService.getSafeRandomLocation(
            world,
            radius,
            this.randomTeleportSettings.randomTeleportAttempts()
        ).thenCompose(location -> this.createTeleport(player, location));
    }

    private CompletableFuture<RandomTeleportResult> createTeleport(Player player, Location location) {
        if (player.hasPermission(RTP_BYPASS_PERMISSION)) {
            return this.randomTeleportService.teleport(player);
        }

        if (this.teleportTaskService.isInTeleport(player.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.teleport().teleportTaskAlreadyExist())
                .player(player.getUniqueId())
                .send();

            return CompletableFuture.completedFuture(new RandomTeleportResult(false, player.getLocation()));
        }

        Location playerLocation = player.getLocation();
        Position playerPosition = PositionAdapter.convert(playerLocation);
        Position destination = PositionAdapter.convert(location);

        Duration time = this.randomTeleportSettings.randomTeleportTime();
        Teleport teleport =
            this.teleportTaskService.createTeleport(player.getUniqueId(), playerPosition, destination, time);

        return teleport.getResult()
            .thenApply(result -> new RandomTeleportResult(result == TeleportResult.SUCCESS, location));
    }
}
