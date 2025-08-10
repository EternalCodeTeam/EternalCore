package com.eternalcode.core.feature.warp.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpInventory;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.feature.warp.WarpSettings;
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

    private static final int MAX_WARPS_IN_GUI = 56;

    private final WarpService warpService;
    private final WarpInventory warpInventory;
    private final NoticeService noticeService;
    private final WarpSettings warpSettings;

    @Inject
    SetWarpCommand(
        WarpService warpService,
        WarpInventory warpInventory,
        NoticeService noticeService,
        WarpSettings warpSettings
    ) {
        this.warpService = warpService;
        this.warpInventory = warpInventory;
        this.noticeService = noticeService;
        this.warpSettings = warpSettings;
    }

    @Execute
    @DescriptionDocs(description = "Create warp")
    void add(@Context Player player, @Arg String warpName) {
        UUID uniqueId = player.getUniqueId();

        this.createWarp(player, warpName, uniqueId);
    }

    private void createWarp(Player player, String warp, UUID uniqueId) {
        if (this.warpService.exists(warp)) {
            this.noticeService.create()
                .player(uniqueId)
                .notice(translation -> translation.warp().warpAlreadyExists())
                .placeholder("{WARP}", warp)
                .send();

            return;
        }

        Warp createdWarp = this.warpService.createWarp(warp, player.getLocation());

        this.noticeService.create()
            .player(uniqueId)
            .notice(translation -> translation.warp().create())
            .placeholder("{WARP}", warp)
            .send();

        if (this.warpSettings.autoAddNewWarps()) {
            if (this.warpService.getWarps().size() <= MAX_WARPS_IN_GUI) {
                this.warpInventory.addWarp(createdWarp);

                this.noticeService.create()
                    .player(uniqueId)
                    .notice(translation -> translation.warp().itemAdded())
                    .send();

                return;
            }

            this.noticeService.create()
                .player(uniqueId)
                .notice(translation -> translation.warp().itemLimit())
                .placeholder("{LIMIT}", String.valueOf(MAX_WARPS_IN_GUI))
                .send();
        }
    }
}
