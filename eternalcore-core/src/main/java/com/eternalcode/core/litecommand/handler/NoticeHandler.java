package com.eternalcode.core.litecommand.handler;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteHandler(Notice.class)
public class NoticeHandler implements ResultHandler<CommandSender, Notice> {

    private final ViewerProvider viewerProvider;
    private final NoticeService noticeService;

    @Inject
    public NoticeHandler(ViewerProvider viewerProvider, NoticeService noticeService) {
        this.viewerProvider = viewerProvider;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, Notice result, ResultHandlerChain<CommandSender> chain) {
        Viewer viewer = this.viewerProvider.any(invocation.sender());

        this.noticeService.create()
            .viewer(viewer)
            .staticNotice(result)
            .send();
    }

}
