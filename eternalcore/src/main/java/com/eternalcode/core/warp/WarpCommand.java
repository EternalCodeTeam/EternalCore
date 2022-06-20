package com.eternalcode.core.warp;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportTaskService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

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

    @Execute
    public void warp(Player player, @Arg Warp warp) {
        teleportTaskService.createTeleport(player.getUniqueId(), player.getLocation(), warp.getLocation(), 5);
    }

    @Execute(route = "add")
    @Permission("eternalcore.warp.create")
    public void add(Player player, @Arg String warp) {
        warpManager.createWarp(warp, player.getLocation());
        noticeService.notice()
            .player(player.getUniqueId())
            .message(messages -> messages.warp().create())
            .placeholder("{name}", warp)
            .send();
    }

    @Execute(route = "remove")
    @Permission("eternalcore.warp.delete")
    public void remove(Player player, @Arg Warp warp) {
        warpManager.removeWarp(warp.getName());
        noticeService.notice()
            .player(player.getUniqueId())
            .message(messages -> messages.warp().remove())
            .placeholder("{name}", warp.getName())
            .send();
    }

}
