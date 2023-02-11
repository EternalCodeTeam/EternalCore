package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.shared.Position;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportTaskService;
import dev.rollczi.litecommands.argument.option.Opt;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.World;
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
        World world = destinationLocation.getWorld();

        if (world == null) {
            this.noticeService.create()
                .notice(translation -> translation.spawn().spawnNoSet())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        if (playerOption.isEmpty()) {
            if (sender.hasPermission("eternalcore.teleport.bypass")) {
                this.teleportService.teleport(sender, destinationLocation);

                this.noticeService.create()
                    .notice(translation -> translation.teleport().teleported())
                    .player(sender.getUniqueId())
                    .send();

                return;
            }

            if (this.teleportTaskService.inTeleport(sender.getUniqueId())) {
                this.noticeService.create()
                    .notice(translation -> translation.teleport().teleportTaskAlreadyExist())
                    .player(sender.getUniqueId())
                    .send();

                return;
            }

            this.teleportTaskService.createTeleport(sender.getUniqueId(), PositionAdapter.convert(sender.getLocation()), PositionAdapter.convert(destinationLocation), Duration.ofSeconds(5));

            this.noticeService.create()
                .notice(translation -> translation.teleport().teleporting())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        Player player = playerOption.get();

        this.teleportService.teleport(player, destinationLocation);

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnTeleportedBy())
            .placeholder("{PLAYER}", sender.getName())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnTeleportedOther())
            .placeholder("{PLAYER}", player.getName())
            .player(sender.getUniqueId())
            .send();
    }
}
