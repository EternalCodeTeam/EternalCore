package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "rtp", aliases = "randomteleport")
public class RandomTeleportCommand {

    private static final Placeholders<Player> PLACEHOLDERS = Placeholders.<Player>builder()
        .with("{PLAYER}", Player::getName)
        .with("{WORLD}", player -> player.getWorld().getName())
        .with("{X}", player -> String.valueOf(player.getLocation().getBlockX()))
        .with("{Y}", player -> String.valueOf(player.getLocation().getBlockY()))
        .with("{Z}", player -> String.valueOf(player.getLocation().getBlockZ()))
        .build();

    private final NoticeService noticeService;
    private final RandomTeleportService randomTeleportService;

    @Inject
    public RandomTeleportCommand(NoticeService noticeService, RandomTeleportService randomTeleportService) {
        this.noticeService = noticeService;
        this.randomTeleportService = randomTeleportService;
    }

    @Execute
    @Permission("eternalcore.rtp")
    @DescriptionDocs(description = "Teleportation of the sender to a random location.")
    void executeSelf(Player player) {
        this.noticeService.create()
            .notice(translation -> translation.randomTeleport().randomTeleportStarted())
            .player(player.getUniqueId())
            .send();

        this.randomTeleportService.teleport(player)
            .whenCompleteAsync((result, error) -> {
                if (error != null || !result.success()) {
                    this.handleTeleportFailure(player);
                    return;
                }

                this.handleTeleportSuccess(player);
            });
    }

    @Execute
    @Permission("eternalcore.rtp.other")
    @DescriptionDocs(description = "Teleportation of a player to a random location.", arguments = "<player>")
    void executeOther(Viewer sender, @Arg Player player) {
        this.noticeService.create()
            .notice(translation -> translation.randomTeleport().randomTeleportStarted())
            .player(player.getUniqueId())
            .send();

        this.randomTeleportService.teleport(player)
            .whenCompleteAsync((result, error) -> {
                if (error != null || !result.success()) {
                    this.handleTeleportFailure(player);
                    return;
                }

                this.handleTeleportSuccess(player);
                this.handleAdminTeleport(sender, player);
            });
    }

    private void handleTeleportFailure(Player player) {
        this.noticeService.create()
            .notice(translation -> translation.randomTeleport().randomTeleportFailed())
            .player(player.getUniqueId())
            .send();
    }

    private void handleTeleportSuccess(Player player) {
        this.noticeService.player(player.getUniqueId(),
            translation -> translation.randomTeleport().teleportedToRandomLocation(),
            PLACEHOLDERS.toFormatter(player));
    }

    private void handleAdminTeleport(Viewer sender, Player player) {
        this.noticeService.viewer(sender,
            translation -> translation.randomTeleport().teleportedSpecifiedPlayerToRandomLocation(),
            PLACEHOLDERS.toFormatter(player));
    }
}
