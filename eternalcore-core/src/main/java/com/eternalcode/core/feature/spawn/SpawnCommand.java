package com.eternalcode.core.feature.spawn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.spawn.messages.SpawnMessages;
import com.eternalcode.core.feature.teleport.apiteleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "spawn")
class SpawnCommand {

    private static final String SPAWN_TELEPORT_BYPASS = "eternalcore.spawn.bypass";

    private final TranslationManager translationManager;
    private final TeleportService teleportService;
    private final NoticeService noticeService;
    private final SpawnService spawnService;

    @Inject
    SpawnCommand(
        TranslationManager translationManager,
        TeleportService teleportService,
        NoticeService noticeService,
        SpawnService spawnService
    ) {
        this.translationManager = translationManager;
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
    void executeSelf(@Context Player sender) {
        SpawnMessages spawnMessages = this.translationManager.getDefaultMessages().spawn();
        Location spawnLocation = this.spawnService.getSpawnLocation();

        if (spawnLocation == null) {
            this.noticeService.create()
                .notice(spawnMessages.spawnNoSet())
                .player(sender.getUniqueId())
                .send();
            return;
        }


        this.noticeService.create()
            .notice(spawnMessages.start())
            .player(sender.getUniqueId())
            .send();

        this.teleportService.teleportWithDelay(sender, spawnLocation, spawnMessages).thenAccept(result -> {
            switch (result) {
                case SUCCESS -> this.noticeService.player(sender.getUniqueId(), translation -> translation.spawn().succes());
                case ALREADY_IN_TELEPORT -> this.noticeService.player(sender.getUniqueId(), translation -> translation.spawn().failtureAlreadyInTeleport());
                case CANCELLED_WORLD_CHANGE -> this.noticeService.player(sender.getUniqueId(), translation -> translation.spawn().failureAfterChangeWorld());
                case CANCELLED_DAMAGE -> this.noticeService.player(sender.getUniqueId(), translation -> translation.spawn().failureAfterTakingDamage());
                case CANCELLED_MOVE -> this.noticeService.player(sender.getUniqueId(), translation -> translation.spawn().failtureAfterMoved());
            }
        });
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
