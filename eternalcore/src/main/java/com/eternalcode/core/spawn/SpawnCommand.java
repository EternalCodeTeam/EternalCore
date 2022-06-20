package com.eternalcode.core.spawn;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportTaskService;

import dev.rollczi.litecommands.argument.option.Opt;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "spawn")
@Permission("eternalcore.spawn")
public class SpawnCommand {

    private final LocationsConfiguration locations;
    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;
    private final NoticeService noticeService;

    public SpawnCommand(LocationsConfiguration locations, NoticeService noticeService, TeleportTaskService teleportTaskService, TeleportService teleportService) {
        this.teleportTaskService = teleportTaskService;
        this.locations = locations;
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute
    public void execute(Player sender, @Opt Option<Player> playerOption) {
        Location destinationLocation = this.locations.spawn;

        if (destinationLocation == null || destinationLocation.getWorld() == null) {
            this.noticeService.notice()
                .message(messages -> messages.other().spawnNoSet())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        if (playerOption.isEmpty()) {
            if (sender.hasPermission("eternalcore.teleport.bypass")) { //TODO: Move to teleport service
                this.teleportService.teleport(sender, destinationLocation);

                this.noticeService.notice()
                    .message(messages -> messages.teleport().taskTeleported())
                    .player(sender.getUniqueId())
                    .send();

                return;
            }

            if (this.teleportTaskService.inTeleport(sender.getUniqueId())) {
                this.noticeService.notice()
                    .message(messages -> messages.teleport().taskTeleportAlreadyExist())
                    .player(sender.getUniqueId())
                    .send();

                return;
            }

            this.teleportTaskService.createTeleport(sender.getUniqueId(), sender.getLocation(), destinationLocation, 5);

            this.noticeService.notice()
                .message(messages -> messages.teleport().taskTeleporting())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        Player player = playerOption.get();

        this.teleportService.teleport(player, destinationLocation);

        this.noticeService.notice()
            .message(messages -> messages.other().spawnTeleportedBy())
            .placeholder("{PLAYER}", sender.getName())
            .player(player.getUniqueId())
            .send();

        this.noticeService.notice()
            .message(messages -> messages.other().spawnTeleportedOther())
            .placeholder("{PLAYER}", player.getName())
            .player(sender.getUniqueId())
            .send();
    }
}
