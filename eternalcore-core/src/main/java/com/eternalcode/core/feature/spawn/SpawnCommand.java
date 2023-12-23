package com.eternalcode.core.feature.spawn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.shared.Position;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "spawn")
class SpawnCommand {

    private final LocationsConfiguration locations;
    private final SpawnSettings spawnSettings;
    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;
    private final NoticeService noticeService;

    @Inject
    SpawnCommand(LocationsConfiguration locations, SpawnSettings spawnSettings, NoticeService noticeService, TeleportTaskService teleportTaskService, TeleportService teleportService) {
        this.teleportTaskService = teleportTaskService;
        this.locations = locations;
        this.spawnSettings = spawnSettings;
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute
    @Permission("eternalcore.spawn")
    @DescriptionDocs(description = "Teleports you to spawn location")
    void executeSelf(@Context Player sender) {
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

        this.teleportTaskService.createTeleport(sender.getUniqueId(), PositionAdapter.convert(sender.getLocation()), PositionAdapter.convert(destinationLocation), this.spawnSettings.teleportationTimeToSpawn());

        this.noticeService.create()
            .notice(translation -> translation.teleport().teleporting())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.spawn.other")
    @DescriptionDocs(description = "Teleports specified player to spawn location", arguments = "<player>")
    void execute(@Context Viewer sender, @Arg Player player) {
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
            .placeholder("{PLAYER}", sender.getName())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnTeleportedOther())
            .placeholder("{PLAYER}", player.getName())
            .viewer(sender)
            .send();
    }
}
