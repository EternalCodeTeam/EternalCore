package com.eternalcode.core.command.implementation.spawn;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportTaskService;

import dev.rollczi.litecommands.argument.option.Opt;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.time.Duration;

@Route(name = "spawn")
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
    void execute(Player sender, @Opt Option<Player> playerOption) {
        Location destinationLocation = PositionAdapter.convert(this.locations.spawn);

        if (destinationLocation == null || destinationLocation.getWorld() == null) {
            this.noticeService.create()
                .notice(messages -> messages.other().spawnNoSet())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        if (playerOption.isEmpty()) {
            if (sender.hasPermission("eternalcore.teleport.bypass")) {
                this.teleportService.teleport(sender, destinationLocation);

                this.noticeService.create()
                    .notice(messages -> messages.teleport().teleported())
                    .player(sender.getUniqueId())
                    .send();

                return;
            }

            if (this.teleportTaskService.inTeleport(sender.getUniqueId())) {
                this.noticeService.create()
                    .notice(messages -> messages.teleport().teleportTaskAlreadyExist())
                    .player(sender.getUniqueId())
                    .send();

                return;
            }

            this.teleportTaskService.createTeleport(sender.getUniqueId(), PositionAdapter.convert(sender.getLocation()), PositionAdapter.convert(destinationLocation), Duration.ofSeconds(5));

            this.noticeService.create()
                .notice(messages -> messages.teleport().teleporting())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        Player player = playerOption.get();

        this.teleportService.teleport(player, destinationLocation);

        this.noticeService.create()
            .notice(messages -> messages.other().spawnTeleportedBy())
            .placeholder("{PLAYER}", sender.getName())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(messages -> messages.other().spawnTeleportedOther())
            .placeholder("{PLAYER}", player.getName())
            .player(sender.getUniqueId())
            .send();
    }
}
