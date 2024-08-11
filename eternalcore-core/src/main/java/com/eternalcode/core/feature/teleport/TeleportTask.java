package com.eternalcode.core.feature.teleport;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.multification.notice.Notice;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.utilities.StringUtils;

@Task(delay = 1L, period = 1L, unit = TimeUnit.SECONDS)
class TeleportTask implements Runnable {

    private final NoticeService noticeService;
    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;
    private final Server server;

    @Inject
    TeleportTask(
        NoticeService noticeService,
        TeleportTaskService teleportTaskService,
        TeleportService teleportService,
        Server server
    ) {
        this.noticeService = noticeService;
        this.teleportTaskService = teleportTaskService;
        this.teleportService = teleportService;
        this.server = server;
    }

    @Override
    public void run() {
        List<Teleport> teleports = List.copyOf(this.teleportTaskService.getTeleports());

        for (Teleport teleport : teleports) {
            UUID uuid = teleport.getPlayerUniqueId();

            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                this.teleportTaskService.removeTeleport(uuid);
                continue;
            }

            Instant now = Instant.now();
            Instant teleportMoment = teleport.getTeleportMoment();

            if (now.isBefore(teleportMoment)) {
                Duration duration = Duration.between(now, teleportMoment);

                this.noticeService.create()
                    .notice(translation -> translation.teleport().teleportTimerFormat())
                    .placeholder("{TIME}", DurationUtil.format(duration, true))
                    .player(player.getUniqueId())
                    .send();
                continue;
            }

            if (this.hasPlayerMovedDuringTeleport(player, teleport)) {
                this.teleportTaskService.removeTeleport(uuid);
                teleport.completeResult(TeleportResult.MOVED_DURING_TELEPORT);

                this.noticeService.create()
                    .notice(translation -> Notice.actionbar(StringUtils.EMPTY))
                    .notice(translation -> translation.teleport().teleportTaskCanceled())
                    .player(player.getUniqueId())
                    .send();

                continue;
            }

            this.completeTeleport(player, teleport);
        }
    }

    private void completeTeleport(Player player, Teleport teleport) {
        Location destinationLocation = PositionAdapter.convert(teleport.getDestinationLocation());
        UUID uuid = teleport.getPlayerUniqueId();

        this.teleportService.teleport(player, destinationLocation);
        this.teleportTaskService.removeTeleport(uuid);

        teleport.completeResult(TeleportResult.SUCCESS);

        this.noticeService.create()
            .notice(translation -> translation.teleport().teleported())
            .player(player.getUniqueId())
            .send();
    }

    private boolean hasPlayerMovedDuringTeleport(Player player, Teleport teleport) {
        Location startLocation = PositionAdapter.convert(teleport.getStartLocation());

        return player.getLocation().distance(startLocation) > 0.5;
    }

}

