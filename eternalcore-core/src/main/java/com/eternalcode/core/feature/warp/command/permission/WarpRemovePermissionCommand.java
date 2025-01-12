package com.eternalcode.core.feature.warp.command.permission;

import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.feature.warp.command.permission.argument.WarpPermissionEntry;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "warp-permission remove")
@Permission("eternalcore.warp.changepermissions")
public class WarpRemovePermissionCommand {

    private final WarpService warpService;
    private final NoticeService noticeService;

    @Inject
    public WarpRemovePermissionCommand(WarpService warpService, NoticeService noticeService) {
        this.warpService = warpService;
        this.noticeService = noticeService;
    }

    @Execute
    void removePermission(
        @Context Player player,
        @Arg WarpPermissionEntry entry
    ) {
        Warp warp = entry.warp();
        String permission = entry.permission();

        if (!warp.getPermissions().contains(permission)) {
            this.noticeService.create()
                .placeholder("{WARP}", warp.getName())
                .placeholder("{PERMISSION}", permission)
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().permissionDoesNotExist())
                .send();
            return;
        }

        this.warpService.removePermission(warp.getName(), permission);

        this.noticeService.create()
            .placeholder("{WARP}", warp.getName())
            .placeholder("{PERMISSION}", permission)
            .player(player.getUniqueId())
            .notice(translation -> translation.warp().removePermission())
            .send();
    }
}
