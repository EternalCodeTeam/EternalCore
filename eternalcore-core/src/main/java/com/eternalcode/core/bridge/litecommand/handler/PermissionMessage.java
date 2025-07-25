package com.eternalcode.core.bridge.litecommand.handler;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.ViewerService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import org.bukkit.command.CommandSender;

@LiteHandler(MissingPermissions.class)
class PermissionMessage implements MissingPermissionsHandler<CommandSender> {

    private final ViewerService viewerService;
    private final NoticeService noticeService;

    @Inject
    public PermissionMessage(ViewerService viewerService, NoticeService noticeService) {
        this.viewerService = viewerService;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> chain) {
        Viewer viewer = this.viewerService.any(invocation.sender());
        String permissions = missingPermissions.asJoinedText();

        this.noticeService.create()
            .notice(translation -> translation.argument().permissionMessage())
            .placeholder("{PERMISSIONS}", permissions)
            .viewer(viewer)
            .send();
    }

}
