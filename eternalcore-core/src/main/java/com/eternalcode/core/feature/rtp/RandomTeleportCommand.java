package com.eternalcode.core.feature.rtp;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Route(name = "rtp", aliases = "randomteleport")
@Permission("eternalcore.rtp")
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
    private final Server server;

    public RandomTeleportCommand(NoticeService noticeService, RandomTeleportService randomTeleportService, Server server) {
        this.noticeService = noticeService;
        this.randomTeleportService = randomTeleportService;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Teleportation of sender to a random location.")
    void executeSelf(Player player) {
        this.noticeService.create()
            .notice(translation -> translation.teleport().randomTeleportProcessBegin())
            .player(player.getUniqueId())
            .send();

        this.randomTeleportService.teleport(player).whenCompleteAsync((result, error) -> {
            if (error != null || !result.success()) {
                return;
            }

            this.noticeService.player(player.getUniqueId(), translation -> translation.teleport().teleportedToRandomLocation(), PLACEHOLDERS.toFormatter(player));
        });
    }

    @Execute
    @DescriptionDocs(description = "Teleportation of player to a random location.", arguments = "<player>")
    void executeOther(Viewer sender, @Arg Player player) {
        this.noticeService.create()
            .notice(translation -> translation.teleport().randomTeleportProcessBegin())
            .player(player.getUniqueId())
            .send();

        this.randomTeleportService.teleport(player).whenCompleteAsync((result, error) -> {
            if (error != null || !result.success()) {
                return;
            }

            this.noticeService.player(player.getUniqueId(), translation -> translation.teleport().teleportedToRandomLocation(), PLACEHOLDERS.toFormatter(player));

            this.noticeService.viewer(sender, translation -> translation.teleport().teleportedToRandomLocationAdmin(), PLACEHOLDERS.toFormatter(player));
        });
    }
}
