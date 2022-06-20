package com.eternalcode.core.teleport;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.shared.Adapter;
import com.eternalcode.core.util.DurationUtil;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.utilities.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class TeleportTask implements Runnable {

    private final NoticeService noticeService;
    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;
    private final Server server;

    public TeleportTask(NoticeService noticeService, TeleportTaskService teleportTaskService, TeleportService teleportService, Server server) {
        this.noticeService = noticeService;
        this.teleportTaskService = teleportTaskService;
        this.teleportService = teleportService;
        this.server = server;
    }

    @Override
    public void run() {
        for (Teleport teleport : this.teleportTaskService.getTeleports()) {
            Location destinationLocation = Adapter.convert(teleport.getDestinationLocation());
            Location startLocation = Adapter.convert(teleport.getStartLocation());
            UUID uuid = teleport.getUuid();
            Instant teleportMoment = teleport.getTeleportMoment();

            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                continue;
            }

            if (player.getLocation().distance(startLocation) > 0.5) {
                this.teleportTaskService.removeTeleport(uuid);

                this.noticeService.notice()
                    .notice(NoticeType.ACTIONBAR, messages -> StringUtils.EMPTY)
                    .message(messages -> messages.teleport().taskCanceled())
                    .player(player.getUniqueId())
                    .send();

                continue;
            }

            Instant now = Instant.now();

            if (now.isBefore(teleportMoment)) {
                Duration duration = Duration.between(now, teleportMoment);

                this.noticeService.notice()
                    .notice(messages -> messages.teleport().taskTimer())
                    .placeholder("{TIME}", DurationUtil.format(duration))
                    .player(player.getUniqueId())
                    .send();

                continue;
            }

            this.teleportService.teleport(player, destinationLocation);
            this.teleportTaskService.removeTeleport(uuid);

            this.noticeService.notice()
                .notice(NoticeType.ACTIONBAR, messages -> messages.teleport().taskTeleported())
                .message(messages -> messages.teleport().taskTeleported())
                .player(player.getUniqueId())
                .send();
        }
    }
}

