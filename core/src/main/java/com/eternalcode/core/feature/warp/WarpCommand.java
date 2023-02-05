package com.eternalcode.core.feature.warp;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportTaskService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

import java.time.Duration;

@Route(name = "warp")
@Permission("eternalcore.warp")
public class WarpCommand {

    private final NoticeService noticeService;
    private final WarpManager warpManager;
    private final TeleportTaskService teleportTaskService;

    public WarpCommand(NoticeService noticeService, WarpManager warpManager, TeleportTaskService teleportTaskService) {
        this.noticeService = noticeService;
        this.warpManager = warpManager;
        this.teleportTaskService = teleportTaskService;
    }

    @Execute(required = 1)
    void warp(Player player, @Arg Warp warp) {
        this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()), warp.getPosition(), Duration.ofSeconds(5));
    }

    @Execute(route = "add", required = 1)
    @Permission("eternalcore.warp.create")
    void add(Player player, @Arg String warp) {
        if (this.warpManager.warpExists(warp)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .placeholder("{WARP}", warp)
                .notice(translation -> translation.warp().warpAlreadyExists())
                .send();

            return;
        }

        this.warpManager.createWarp(warp, PositionAdapter.convert(player.getLocation()));

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.warp().create())
            .placeholder("{NAME}", warp)
            .send();
    }

    @Execute(route = "remove", required = 1)
    @Permission("eternalcore.warp.delete")
    void remove(Player player, @Arg Warp warp) {
        this.warpManager.removeWarp(warp.getName());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.warp().remove())
            .placeholder("{NAME}", warp.getName())
            .send();
    }

}
