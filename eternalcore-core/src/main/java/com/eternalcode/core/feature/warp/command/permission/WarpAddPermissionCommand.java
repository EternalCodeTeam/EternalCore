package com.eternalcode.core.feature.warp.command.permission;

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

@Command(name = "warp-permission add")
@Permission("eternalcore.warp.changepermissions")
public class WarpAddPermissionCommand {

    private final WarpService warpService;
    private final NoticeService noticeService;

    @Inject
    public WarpAddPermissionCommand(WarpService warpService, NoticeService noticeService) {
        this.warpService = warpService;
        this.noticeService = noticeService;
    }

    @Execute
    void addPermission(@Context Player player, @Arg Warp warp, @Arg String... permissions) {
        UUID uniqueId = player.getUniqueId();

        if (permissions.length == 0) {
            this.noticeService.create()
                .player(uniqueId)
                .notice(translation -> translation.warp().noPermissionsProvided())
                .send();
            return;
        }

        this.warpService.addPermissions(warp.getName(), permissions);

        this.noticeService.create()
            .player(uniqueId)
            .placeholder("{WARP}", warp.getName())
            .notice(translation -> translation.warp().addPermissions())
            .send();
    }

}
