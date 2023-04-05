package com.eternalcode.core.feature.warp;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportTaskService;
import com.eternalcode.core.user.User;
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
    private final WarpInventory warpInventory;

    public WarpCommand(NoticeService noticeService, WarpManager warpManager, TeleportTaskService teleportTaskService, WarpInventory warpInventory) {
        this.noticeService = noticeService;
        this.warpManager = warpManager;
        this.teleportTaskService = teleportTaskService;
        this.warpInventory = warpInventory;
    }

    @Execute(required = 0)
    void warp(Player player, User user) {
        this.warpInventory.openInventory(player, user.getLanguage());
    }

    @Execute(required = 1)
    void warp(Player player, @Arg Warp warp) {
        if (player.hasPermission("eternalcore.warp.bypass")) {
            this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()), warp.getPosition(), Duration.ZERO);
            return;
        }

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
            .placeholder("{WARP}", warp)
            .send();
    }

    @Execute(route = "remove", required = 1)
    @Permission("eternalcore.warp.delete")
    void remove(Player player, @Arg Warp warp) {
        this.warpManager.removeWarp(warp.getName());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.warp().remove())
            .placeholder("{WARP}", warp.getName())
            .send();
    }

}
