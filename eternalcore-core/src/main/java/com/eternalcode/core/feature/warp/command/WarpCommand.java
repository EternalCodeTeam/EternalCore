package com.eternalcode.core.feature.warp.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpInventory;
import com.eternalcode.core.feature.warp.WarpManager;
import com.eternalcode.core.feature.warp.WarpTeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@RootCommand
@Permission("eternalcore.warp")
class WarpCommand {

    private final PluginConfiguration config;
    private final WarpInventory warpInventory;
    private final WarpManager warpManager;
    private final NoticeService noticeService;
    private final WarpTeleportService warpTeleportService;

    @Inject
    WarpCommand(
        PluginConfiguration config,
        WarpInventory warpInventory,
        WarpManager warpManager,
        NoticeService noticeService,
        WarpTeleportService warpTeleportService
    ) {
        this.config = config;
        this.warpInventory = warpInventory;
        this.warpManager = warpManager;
        this.noticeService = noticeService;
        this.warpTeleportService = warpTeleportService;
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
        this.warpTeleportService.teleport(player, warp);
    }
}
