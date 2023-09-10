package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportTaskService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.root.RootRoute;
import org.bukkit.entity.Player;

import java.time.Duration;

@RootRoute
@Permission("eternalcore.warp")
public class WarpCommand {

    private final TeleportTaskService teleportTaskService;
    private final PluginConfiguration config;
    private final WarpInventory warpInventory;
    private final NoticeService noticeService;
    private final WarpManager warpManager;

    public WarpCommand(NoticeService noticeService, WarpManager warpManager, TeleportTaskService teleportTaskService, PluginConfiguration config, WarpInventory warpInventory) {
        this.noticeService = noticeService;
        this.warpManager = warpManager;
        this.teleportTaskService = teleportTaskService;
        this.config = config;
        this.warpInventory = warpInventory;
    }

    @Execute(route = "warp", required = 0)
    @DescriptionDocs(description = "Open warp inventory, optionally you can disable this feature in config, if feature is disable eternalcore show all available warps")
    void warp(Player player, User user) {
        if (!this.config.warp.inventoryEnabled) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().available())
                .placeholder("{WARPS}", String.join(", ", this.warpManager.getNamesOfWarps()))
                .send();

            return;
        }

        this.warpInventory.openInventory(player, user.getLanguage());
    }

    @Execute(route = "warp", required = 1)
    @DescriptionDocs(description = "Teleport to warp, if player has permission eternalcore.warp.bypass teleport will be instant")
    void warp(Player player, @Arg Warp warp) {
        if (player.hasPermission("eternalcore.warp.bypass")) {
            this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()), warp.getPosition(), Duration.ZERO);
            return;
        }

        this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()), warp.getPosition(), Duration.ofSeconds(5));
    }

    @Execute(route = "setwarp", required = 1)
    @Permission("eternalcore.warp.create")
    @DescriptionDocs(description = "Create warp")
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

    @Execute(route = "delwarp", required = 1)
    @Permission("eternalcore.warp.delete")
    @DescriptionDocs(description = "Remove warp")
    void remove(Player player, @Arg Warp warp) {
        this.warpManager.removeWarp(warp.getName());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.warp().remove())
            .placeholder("{WARP}", warp.getName())
            .send();
    }

}
