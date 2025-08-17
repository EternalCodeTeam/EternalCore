package com.eternalcode.core.feature.spawn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "spawn")
class SpawnCommand {

    private static final String SPAWN_TELEPORT_BYPASS = "eternalcore.spawn.bypass";

    private final TeleportTaskService teleportTaskService;
    private final SpawnJoinSettings spawnJoinSettings;
    private final TeleportService teleportService;
    private final NoticeService noticeService;
    private final SpawnService spawnService;

    @Inject
    SpawnCommand(
        TeleportTaskService teleportTaskService,
        SpawnJoinSettings spawnJoinSettings,
        TeleportService teleportService,
        NoticeService noticeService,
        SpawnService spawnService
    ) {
        this.teleportTaskService = teleportTaskService;
        this.spawnJoinSettings = spawnJoinSettings;
        this.teleportService = teleportService;
        this.noticeService = noticeService;
        this.spawnService = spawnService;
    }

    @Execute
    @Permission("eternalcore.spawn")
    @DescriptionDocs(description = "Teleports you to spawn location")
    @PermissionDocs(
        name = "Spawn teleport bypass",
        permission = SPAWN_TELEPORT_BYPASS,
        description = "Allows you to bypass spawn teleportation time"
    )
    void executeSelf(@Sender Player sender) {
        Location spawnLocation = this.spawnService.getSpawnLocation();

        if (spawnLocation == null) {
            this.noticeService.create()
                .notice(translation -> translation.spawn().spawnNoSet())
                .player(sender.getUniqueId())
                .send();
            return;
        }

        if (sender.hasPermission(SPAWN_TELEPORT_BYPASS)) {
            this.teleportService.teleport(sender, spawnLocation);

            this.noticeService.create()
                .notice(translation -> translation.teleport().teleported())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        if (this.teleportTaskService.isInTeleport(sender.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.teleport().teleportTaskAlreadyExist())
                .player(sender.getUniqueId())
                .send();
            return;
        }

        Duration teleportDelay = this.spawnJoinSettings.spawnTeleportTime();

        this.teleportTaskService.createTeleport(
            sender.getUniqueId(),
            PositionAdapter.convert(sender.getLocation()),
            PositionAdapter.convert(spawnLocation),
            teleportDelay
        );

        this.noticeService.create()
            .notice(translation -> translation.teleport().teleporting())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.spawn.other")
    @DescriptionDocs(description = "Teleports specified player to spawn location", arguments = "<player>")
    void execute(@Context Viewer sender, @Arg Player player) {
        Location spawnLocation = this.spawnService.getSpawnLocation();

        if (spawnLocation == null) {
            this.noticeService.create()
                .notice(translation -> translation.spawn().spawnNoSet())
                .viewer(sender)
                .send();
            return;
        }

        this.teleportService.teleport(player, spawnLocation);

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
