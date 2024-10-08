package com.eternalcode.core.feature.warp.command;

import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

import java.util.UUID;

@Command(name = "removewarp-permission")
@Permission("eternalcore.warp.changepermissions")
public class RemoveWarpPermissionCommand {

    private final WarpService warpService;
    private final NoticeService noticeService;

    @Inject
    public RemoveWarpPermissionCommand(WarpService warpService, NoticeService noticeService) {
        this.warpService = warpService;
        this.noticeService = noticeService;
    }

    @Execute
    void removePermission(@Context Player player, @Arg Warp warp, @Arg String permission) {
        UUID uniqueId = player.getUniqueId();

        if (!warp.getPermissions().contains(permission)) {
            this.noticeService.create()
                .player(uniqueId)
                .placeholder("{PERMISSION}", permission)
                .notice(translation -> translation.warp().permissionDoesNotExist())
                .send();
            return;
        }

        this.warpService.removePermission(warp.getName(), permission);

        this.noticeService.create()
            .player(uniqueId)
            .placeholder("{WARP}", warp.getName())
            .placeholder("{PERMISSION}", permission)
            .notice(translation -> translation.warp().removePermission())
            .send();
    }

}
