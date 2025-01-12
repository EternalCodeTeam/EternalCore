package com.eternalcode.core.feature.warp.command.permission;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "warp-permission add")
@Permission("eternalcore.warp.changepermissions")
public class WarpAddPermissionCommand {

    private final PluginConfiguration config;
    private final WarpService warpService;
    private final NoticeService noticeService;

    @Inject
    public WarpAddPermissionCommand(PluginConfiguration config, WarpService warpService, NoticeService noticeService) {
        this.config = config;
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

        Collection<String> currentPermissions = warp.getPermissions();

        List<String> newPermissions = Arrays.stream(permissions)
            .filter(permission -> !currentPermissions.contains(permission))
            .toList();

        if (newPermissions.isEmpty()) {
            this.noticeService.create()
                .player(uniqueId)
                .placeholder("{WARP}", warp.getName())
                .placeholder("{PERMISSION}", String.join(this.config.format.separator, permissions))
                .notice(translation -> translation.warp().permissionAlreadyExist())
                .send();
            return;
        }
        this.warpService.addPermissions(warp.getName(), newPermissions.toArray(new String[0]));

        this.noticeService.create()
            .player(uniqueId)
            .placeholder("{WARP}", warp.getName())
            .notice(translation -> translation.warp().addPermissions())
            .send();
    }
}
