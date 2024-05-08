package com.eternalcode.core.feature.warp.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpInventory;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "setwarp")
@Permission("eternalcore.setwarp")
class SetWarpCommand {

    private final WarpService warpService;
    private final WarpInventory warpInventory;
    private final NoticeService noticeService;
    private final PluginConfiguration config;

    @Inject
    SetWarpCommand(WarpService warpService, WarpInventory warpInventory, NoticeService noticeService, PluginConfiguration config) {
        this.warpService = warpService;
        this.warpInventory = warpInventory;
        this.noticeService = noticeService;
        this.config = config;
    }

    @Execute
    @DescriptionDocs(description = "Create warp")
    void add(@Context Player player, @Arg String warpName) {
        UUID uniqueId = player.getUniqueId();

        this.createWarp(player, warpName, uniqueId);
    }

    private void createWarp(Player player, String warp, UUID uniqueId) {
        if (this.warpService.warpExists(warp)) {
            this.noticeService.create()
                .player(uniqueId)
                .placeholder("{WARP}", warp)
                .notice(translation -> translation.warp().warpAlreadyExists())
                .send();

            return;
        }

        Warp createdWarp = this.warpService.createWarp(warp, player.getLocation());

        this.noticeService.create()
            .player(uniqueId)
            .notice(translation -> translation.warp().create())
            .placeholder("{WARP}", warp)
            .send();

        if (this.config.warp.autoAddNewWarps) {

            this.warpInventory.addWarp(createdWarp);

            this.noticeService.create()
                .player(uniqueId)
                .notice(translation -> translation.warp().itemAdded())
                .send();
        }
    }
}
