package com.eternalcode.core.feature.warp.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
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
import org.bukkit.entity.Player;

@Command(name = "delwarp")
@Permission("eternalcore.delwarp")
class DelWarpCommand {

    private final WarpService warpService;
    private final NoticeService noticeService;
    private final WarpInventory warpInventory;

    @Inject
    DelWarpCommand(WarpService warpService, NoticeService noticeService, WarpInventory warpInventory) {
        this.warpService = warpService;
        this.noticeService = noticeService;
        this.warpInventory = warpInventory;
    }

    @Execute
    @DescriptionDocs(description = "Remove warp", arguments = "<warp>")
    void remove(@Context Player player, @Arg Warp warp) {
        String name = warp.getName();

        this.removeWarp(player, name);
    }

    private void removeWarp(Player player, String name) {
        if (!this.warpService.exists(name)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().notExist())
                .placeholder("{WARP}", name)
                .send();

            return;
        }

        this.warpService.removeWarp(name);
        this.warpInventory.removeWarp(name);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.warp().remove())
            .placeholder("{WARP}", name)
            .send();

    }
}
