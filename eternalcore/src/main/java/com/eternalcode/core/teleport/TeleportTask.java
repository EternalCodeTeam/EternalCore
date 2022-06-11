package com.eternalcode.core.teleport;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
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
    private final TeleportService teleportService;
    private final Server server;

    public TeleportTask(NoticeService noticeService, TeleportService teleportService, Server server) {
        this.noticeService = noticeService;
        this.teleportService = teleportService;
        this.server = server;
    }

    @Override
    public void run() {
        for (Teleport teleport : this.teleportService.getTeleports()){
            Location destinationLocation = teleport.getDestinationLocation();
            Location startLocation = teleport.getStartLocation();
            UUID uuid = teleport.getUuid();
            Instant teleportMoment = teleport.getTeleportMoment();

            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                continue;
            }

            if (player.getLocation().distance(startLocation) > 0.5) {
                this.teleportService.removeTeleport(uuid);

                this.noticeService.notice()
                    .notice(NoticeType.ACTIONBAR, messages -> StringUtils.EMPTY)
                    .message(messages -> messages.teleport().cancel())
                    .player(player.getUniqueId())
                    .send();

                continue;
            }

            Instant now = Instant.now();

            if (now.isBefore(teleportMoment)) {
                Duration duration = Duration.between(now, teleportMoment);

                this.noticeService.notice()
                    .notice(NoticeType.ACTIONBAR, messages -> messages.teleport().actionBarMessage())
                    .placeholder("{TIME}", DurationUtil.format(duration))
                    .player(player.getUniqueId())
                    .send();

                continue;
            }

            player.teleport(destinationLocation);

            this.teleportService.removeTeleport(uuid);

            this.noticeService.notice()
                .notice(NoticeType.ACTIONBAR, messages -> messages.teleport().teleported())
                .message(messages -> messages.teleport().teleported())
                .player(player.getUniqueId())
                .send();
        }
    }
}

