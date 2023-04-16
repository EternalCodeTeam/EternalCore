package com.eternalcode.core.feature.spawn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.shared.Position;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportTaskService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
    @DescriptionDocs(description = "Teleports you to spawn location")
    void executeSelf(Player sender) {
        Position position = this.locations.spawn;

        if (position.isNoneWorld()) {
            this.noticeService.create()
                .notice(translation -> translation.spawn().spawnNoSet())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        Location destinationLocation = PositionAdapter.convert(this.locations.spawn);

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
    }

    @Execute
    @DescriptionDocs(description = "Teleports specified player to spawn location", arguments = "<player>")
    void execute(Viewer sender, @Arg Player player) {
        Position position = this.locations.spawn;

        if (position.isNoneWorld()) {
            this.noticeService.create()
                .notice(translation -> translation.spawn().spawnNoSet())
                .viewer(sender)
                .send();

            return;
        }

        Location destinationLocation = PositionAdapter.convert(this.locations.spawn);

        this.teleportService.teleport(player, destinationLocation);

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnTeleportedBy())
            .placeholder("{SENDER}", sender.getName())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnTeleportedOther())
            .placeholder("{PLAYER}", player.getName())
            .viewer(sender)
            .send();
    }
}
