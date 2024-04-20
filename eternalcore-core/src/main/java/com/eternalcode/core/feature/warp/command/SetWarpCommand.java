package com.eternalcode.core.feature.warp.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
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
    private final NoticeService noticeService;

    @Inject
    SetWarpCommand(WarpService warpService, NoticeService noticeService) {
        this.warpService = warpService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Create warp")
    void add(@Context Player player, @Arg String warp) {
        UUID uniqueId = player.getUniqueId();

        this.createWarp(player, warp, uniqueId);
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

        this.warpService.createWarp(warp, player.getLocation());

        this.noticeService.create()
            .player(uniqueId)
            .notice(translation -> translation.warp().create())
            .placeholder("{WARP}", warp)
            .send();
    }
}
