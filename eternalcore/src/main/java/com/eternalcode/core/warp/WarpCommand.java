package com.eternalcode.core.warp;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportTaskService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

import java.time.Duration;

@Section(route = "warp")
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
    void add(Player player, @Arg @Name("warp") String warp) {
        this.warpManager.createWarp(warp, PositionAdapter.convert(player.getLocation()));
        this.noticeService.create()
            .player(player.getUniqueId())
            .message(messages -> messages.warp().create())
            .placeholder("{NAME}", warp)
            .send();
    }

    @Execute(route = "remove", required = 1)
    @Permission("eternalcore.warp.delete")
    void remove(Player player, @Arg Warp warp) {
        this.warpManager.removeWarp(warp.getName());

        this.noticeService.create()
            .player(player.getUniqueId())
            .message(messages -> messages.warp().remove())
            .placeholder("{NAME}", warp.getName())
            .send();
    }

}
