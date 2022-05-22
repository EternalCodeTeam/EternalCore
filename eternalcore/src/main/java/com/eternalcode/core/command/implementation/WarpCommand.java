package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.warps.Warp;
import com.eternalcode.core.warps.WarpManager;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "warp")
public class WarpCommand {

    private final NoticeService noticeService;
    private final WarpManager warpManager;
    private final TeleportService teleportService;

    public WarpCommand(NoticeService noticeService, WarpManager warpManager, TeleportService teleportService) {
        this.noticeService = noticeService;
        this.warpManager = warpManager;
        this.teleportService = teleportService;
    }

    @Execute
    public void warp(Player player, @Arg Warp warp) {
        teleportService.createTeleport(player.getUniqueId(), player.getLocation(), warp.getLocation(), 5);
    }

    @Execute(route = "add")
    public void add(Player player, @Arg String warp) {
        warpManager.createWarp(warp, player.getLocation());
        noticeService.notice()
            .player(player.getUniqueId())
            .message(messages -> messages.warp().create())
            .placeholder("{name}", warp)
            .send();
    }

    @Execute(route = "remove")
    public void remove(Player player, @Arg Warp warp) {
        warpManager.removeWarp(warp.getName());
        noticeService.notice()
            .player(player.getUniqueId())
            .message(messages -> messages.warp().remove())
            .placeholder("{name}", warp.getName())
            .send();
    }

}
