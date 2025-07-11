package com.eternalcode.core.bridge.litecommand.handler;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.core.viewer.ViewerService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteHandler(Notice.class)
class NoticeHandler implements ResultHandler<CommandSender, Notice> {

    private final ViewerService viewerService;
    private final NoticeService noticeService;

    @Inject
    public NoticeHandler(ViewerService viewerService, NoticeService noticeService) {
        this.viewerService = viewerService;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, Notice result, ResultHandlerChain<CommandSender> chain) {
        Viewer viewer = this.viewerService.any(invocation.sender());

        this.noticeService.create()
            .viewer(viewer)
            .notice(result)
            .send();
    }

}
