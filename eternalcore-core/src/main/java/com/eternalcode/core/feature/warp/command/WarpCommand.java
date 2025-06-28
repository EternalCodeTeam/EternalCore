package com.eternalcode.core.feature.warp.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpInventory;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.feature.warp.WarpTeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.List;
import org.bukkit.entity.Player;

@RootCommand
@Permission("eternalcore.warp")
class WarpCommand {

    private final PluginConfiguration config;
    private final WarpInventory warpInventory;
    private final WarpService warpService;
    private final NoticeService noticeService;
    private final WarpTeleportService warpTeleportService;

    @Inject
    WarpCommand(
        PluginConfiguration config,
        WarpInventory warpInventory,
        WarpService warpService,
        NoticeService noticeService,
        WarpTeleportService warpTeleportService
    ) {
        this.config = config;
        this.warpInventory = warpInventory;
        this.warpService = warpService;
        this.noticeService = noticeService;
        this.warpTeleportService = warpTeleportService;
    }

    @Execute(name = "warp")
    @DescriptionDocs(description = "Open warp inventory, optionally you can disable this feature in config, if feature is disabled eternalcore will show all available warps")
    void warp(@Context Player player) {
        if (!this.config.warp.inventoryEnabled) {
            List<String> list = this.warpService.getWarps().stream().map(Warp::getName).toList();

            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().available())
                .placeholder("{WARPS}", String.join(this.config.format.separator, list))
                .send();

            return;
        }

        if (this.warpService.getWarps().isEmpty()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().noWarps())
                .send();

            return;
        }

        this.warpInventory.openInventory(player);
    }

    @Execute(name = "warp")
    @DescriptionDocs(description = "Teleport to warp", arguments = "<warp>")
    void warp(@Context Player player, @Arg Warp warp) {
        if (!warp.hasPermissions(player)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .placeholder("{WARP}", warp.getName())
                .placeholder("{PERMISSIONS}", String.join(this.config.format.separator, warp.getPermissions()))
                .notice(translation -> translation.warp().noPermission())
                .send();
            return;
        }

        this.warpTeleportService.teleport(player, warp);
    }

}
