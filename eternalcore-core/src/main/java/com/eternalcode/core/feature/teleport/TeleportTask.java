package com.eternalcode.core.feature.teleport;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.multification.notice.Notice;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.utilities.StringUtils;

@Task(delay = 1L, period = 1L, unit = TimeUnit.SECONDS)
class TeleportTask implements Runnable {

    private static final int SECONDS_OFFSET = 1;

    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;
    private final NoticeService noticeService;
    private final Server server;

    @Inject
    TeleportTask(
        TeleportTaskService teleportTaskService,
        TeleportService teleportService,
        NoticeService noticeService,
        Server server
    ) {
        this.teleportTaskService = teleportTaskService;
        this.teleportService = teleportService;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Override
    public void run() {
        for (Teleport teleport : this.teleportTaskService.getTeleports()) {
            UUID uuid = teleport.getPlayerUniqueId();

            Player player = this.server.getPlayer(uuid);

            if (teleport.isCancelled()) {
                this.handleCancel(player, teleport);
                return;
            }

            if (this.hasPlayerMovedDuringTeleport(player, teleport)) {
                teleport.completeResult(TeleportResult.CANCELLED_MOVE);
                continue;
            }

            Instant now = Instant.now();
            Instant teleportMoment = teleport.getTeleportMoment();

            if (now.isBefore(teleportMoment)) {
                Duration duration = Duration.between(now, teleportMoment);

                this.noticeService.create()
                    .notice(teleport.messages().countDown())
                    .placeholder("{TIME}", DurationUtil.format(duration.plusSeconds(SECONDS_OFFSET), true))
                    .player(player.getUniqueId())
                    .send();

                continue;
            }

            this.completeTeleport(player, teleport);
        }
    }

    private void handleCancel(Player player, Teleport teleport) {
        if (player == null) {
            this.teleportTaskService.removeTeleport(teleport.getPlayerUniqueId());
            return;
        }

        TeleportResult result = teleport.getResult().getNow(null);

        Notice cancelNotice = switch (result) {
            case CANCELLED_DAMAGE -> teleport.messages().failureAfterTakingDamage();
            case CANCELLED_WORLD_CHANGE -> teleport.messages().failureAfterChangeWorld();
            default -> teleport.messages().failtureAfterMoved();
        };

        this.noticeService.create()
            .notice(cancelNotice)
            .player(player.getUniqueId())
            .send();

        this.clearNotices(player);

        this.teleportTaskService.removeTeleport(teleport.getPlayerUniqueId());
    }

    private void completeTeleport(Player player, Teleport teleport) {
        Location destinationLocation = PositionAdapter.convert(teleport.getDestinationLocation());
        UUID uuid = teleport.getPlayerUniqueId();

        this.teleportService.teleport(player, destinationLocation);
        this.teleportTaskService.removeTeleport(uuid);

        teleport.completeResult(TeleportResult.SUCCESS);

        this.clearNotices(player);

        this.noticeService.create()
            .notice(teleport.messages().succes())
            .player(player.getUniqueId())
            .send();
    }

    private boolean hasPlayerMovedDuringTeleport(Player player, Teleport teleport) {
        Location startLocation = PositionAdapter.convert(teleport.getStartLocation());
        Location currentLocation = player.getLocation();

        if (!currentLocation.getWorld().equals(startLocation.getWorld())) {
            return true;
        }

        return currentLocation.distance(startLocation) > 0.5;
    }

    private void clearNotices(Player player) {
        this.noticeService.create()
            .notice(Notice.actionbar(StringUtils.EMPTY))
            .notice(Notice.title(StringUtils.EMPTY, StringUtils.EMPTY))
            .player(player.getUniqueId())
            .send();
    }
}

