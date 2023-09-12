package com.eternalcode.core.litecommand.handler;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Joiner;

@LiteHandler(RequiredPermissions.class)
public class PermissionMessage implements PermissionHandler<CommandSender> {

    private final ViewerProvider viewerProvider;
    private final NoticeService noticeService;

    @Inject
    public PermissionMessage(ViewerProvider viewerProvider, NoticeService noticeService) {
        this.viewerProvider = viewerProvider;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, RequiredPermissions requiredPermissions) {
        Viewer viewer = this.viewerProvider.any(sender);
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
