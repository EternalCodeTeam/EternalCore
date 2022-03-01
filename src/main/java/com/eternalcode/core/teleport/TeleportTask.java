package com.eternalcode.core.teleport;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.chat.notification.NotificationType;
import com.eternalcode.core.utils.DateUtils;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeleportTask implements Runnable {

    private final AudiencesService audiencesService;
    private final TeleportManager teleportManager;
    private final Server server;

    public TeleportTask(AudiencesService audiencesService, TeleportManager teleportManager, Server server) {
        this.audiencesService = audiencesService;
        this.teleportManager = teleportManager;
        this.server = server;
    }

    @Override
    public void run() {
        for (Teleport teleport : this.teleportManager.getTeleportMap().values()){
            Location location = teleport.getLocation();
            UUID uuid = teleport.getUuid();
            long time = teleport.getTime();

            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                continue;
            }

            if (System.currentTimeMillis() < time) {
                long actionTime = time - System.currentTimeMillis();

                audiencesService.notice()
                    .notice(NotificationType.ACTIONBAR, messages -> messages.teleport().actionBarMessage())
                    .placeholder("{TIME}", DateUtils.durationToString(actionTime))
                    .player(player.getUniqueId())
                    .send();

                continue;
            }
            player.teleportAsync(location);

            this.teleportManager.removeTeleport(uuid);

            audiencesService.notice()
                .notice(NotificationType.ACTIONBAR, messages -> messages.teleport().teleported())
                .message(messages -> messages.teleport().teleported())
                .placeholder("{TIME}", DateUtils.durationToString(time))
                .player(player.getUniqueId())
                .send();
        }
    }
}
