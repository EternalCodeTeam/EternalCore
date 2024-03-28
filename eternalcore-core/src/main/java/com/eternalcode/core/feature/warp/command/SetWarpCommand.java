package com.eternalcode.core.feature.warp.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.warp.WarpManager;
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

    private final WarpManager warpManager;
    private final NoticeService noticeService;

    @Inject
    SetWarpCommand(WarpManager warpManager, NoticeService noticeService) {
        this.warpManager = warpManager;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Create warp")
    void add(@Context Player player, @Arg String warp) {
        UUID uniqueId = player.getUniqueId();

        this.createWarp(player, warp, uniqueId);
    }

    private void createWarp(Player player, String warp, UUID uniqueId) {
        if (this.warpManager.warpExists(warp)) {
            this.noticeService.create()
                .player(uniqueId)
                .placeholder("{WARP}", warp)
                .notice(translation -> translation.warp().warpAlreadyExists())
                .send();

            return;
        }

        this.warpManager.createWarp(warp, player.getLocation());

        this.noticeService.create()
            .player(uniqueId)
            .notice(translation -> translation.warp().create())
            .placeholder("{WARP}", warp)
            .send();
    }
}
