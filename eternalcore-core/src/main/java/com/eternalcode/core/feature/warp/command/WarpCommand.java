package com.eternalcode.core.feature.warp.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.inventory.WarpInventory;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.feature.warp.WarpSettings;
import com.eternalcode.core.feature.warp.WarpTeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.List;
import org.bukkit.entity.Player;

@RootCommand
@Permission("eternalcore.warp")
class WarpCommand {

    private final WarpSettings warpSettings;
    private final WarpInventory warpInventory;
    private final WarpService warpService;
    private final NoticeService noticeService;
    private final WarpTeleportService warpTeleportService;
    private final PluginConfiguration pluginConfiguration;

    @Inject
    WarpCommand(
        WarpSettings warpSettings,
        WarpInventory warpInventory,
        WarpService warpService,
        NoticeService noticeService,
        WarpTeleportService warpTeleportService, PluginConfiguration pluginConfiguration
    ) {
        this.warpSettings = warpSettings;
        this.warpInventory = warpInventory;
        this.warpService = warpService;
        this.noticeService = noticeService;
        this.warpTeleportService = warpTeleportService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute(name = "warp")
    @DescriptionDocs(description = "Open warp inventory, optionally you can disable this feature in config, if feature is disabled eternalcore will show all available warps")
    void warp(@Sender Player player) {
        if (!this.warpSettings.inventoryEnabled()) {
            List<String> list = this.warpService.getWarps().stream().map(Warp::getName).toList();

            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().available())
                .placeholder("{WARPS}", String.join(this.pluginConfiguration.format.separator, list))
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

        this.warpInventory.open(player);
    }

    @Execute(name = "warp")
    @DescriptionDocs(description = "Teleport to warp", arguments = "<warp>")
    void warp(@Sender Player player, @Arg Warp warp) {
        if (!warp.hasPermissions(player)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .placeholder("{WARP}", warp.getName())
                .placeholder("{PERMISSIONS}", String.join(this.pluginConfiguration.format.separator, warp.getPermissions()))
                .notice(translation -> translation.warp().noPermission())
                .send();
            return;
        }

        this.warpTeleportService.teleport(player, warp);
    }

}
