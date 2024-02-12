package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.feature.warp.event.WarpTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.commons.shared.bukkit.position.PositionAdapter;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import org.bukkit.entity.Player;

import java.time.Duration;

@RootCommand
@Permission("eternalcore.warp")
class WarpCommand {

    private final TeleportTaskService teleportTaskService;
    private final PluginConfiguration config;
    private final WarpInventory warpInventory;
    private final NoticeService noticeService;
    private final WarpManager warpManager;
    private final EventCaller eventCaller;

    @Inject
    WarpCommand(NoticeService noticeService, WarpManager warpManager, TeleportTaskService teleportTaskService, PluginConfiguration config, WarpInventory warpInventory, EventCaller eventCaller) {
        this.noticeService = noticeService;
        this.warpManager = warpManager;
        this.teleportTaskService = teleportTaskService;
        this.config = config;
        this.warpInventory = warpInventory;
        this.eventCaller = eventCaller;
    }

    @Execute(name = "warp")
    @DescriptionDocs(description = "Open warp inventory, optionally you can disable this feature in config, if feature is disable eternalcore show all available warps")
    void warp(@Context Player player, @Context User user) {
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

    @Execute(name = "warp")
    @DescriptionDocs(description = "Teleport to warp, if player has permission eternalcore.warp.bypass teleport will be instant", arguments = "<warp>")
    void warp(@Context Player player, @Arg Warp warp) {
        WarpTeleportEvent event = this.eventCaller.callEvent(new WarpTeleportEvent(player, warp));
        if (event.isCancelled()) {
            return;
        }

        if (player.hasPermission("eternalcore.warp.bypass")) {
            this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()), PositionAdapter.convert(warp.getLocation()), Duration.ZERO);
            return;
        }
            this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()), PositionAdapter.convert(warp.getLocation()), Duration.ofSeconds(5));
    }

    @Execute(name = "setwarp")
    @Permission("eternalcore.setwarp")
    @DescriptionDocs(description = "Create warp")
    void add(@Context Player player, @Arg String warp) {
        if (this.warpManager.warpExists(warp)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .placeholder("{WARP}", warp)
                .notice(translation -> translation.warp().warpAlreadyExists())
                .send();

            return;
        }

        this.warpManager.createWarp(warp, player.getLocation());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.warp().create())
            .placeholder("{WARP}", warp)
            .send();
    }

    @Execute(name = "delwarp")
    @Permission("eternalcore.delwarp")
    @DescriptionDocs(description = "Remove warp", arguments = "<warp>")
    void remove(@Context Player player, @Arg Warp warp) {
        this.warpManager.removeWarp(warp.getName());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.warp().remove())
            .placeholder("{WARP}", warp.getName())
            .send();
    }

}
