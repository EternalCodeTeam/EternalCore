package com.eternalcode.core.command.handler;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Joiner;

@LiteHandler(RequiredPermissions.class)
public class PermissionMessage implements PermissionHandler<CommandSender> {

    private final BukkitViewerProvider viewerProvider;
    private final NoticeService noticeService;

    @Inject
    public PermissionMessage(BukkitViewerProvider viewerProvider, NoticeService noticeService) {
        this.viewerProvider = viewerProvider;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, RequiredPermissions requiredPermissions) {
        Viewer viewer = this.viewerProvider.sender(sender);
        String perms = Joiner.on(", ")
            .join(requiredPermissions.getPermissions())
            .toString();

        this.noticeService.create()
            .notice(translation -> translation.argument().permissionMessage())
            .placeholder("{PERMISSIONS}", perms)
            .viewer(viewer)
            .send();
    }

}
