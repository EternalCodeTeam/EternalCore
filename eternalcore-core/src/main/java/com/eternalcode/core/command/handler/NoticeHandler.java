package com.eternalcode.core.command.handler;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import org.bukkit.command.CommandSender;

@LiteHandler(Notice.class)
public class NoticeHandler implements Handler<CommandSender, Notice> {

    private final ViewerProvider viewerProvider;
    private final NoticeService noticeService;

    @Inject
    public NoticeHandler(ViewerProvider viewerProvider, NoticeService noticeService) {
        this.viewerProvider = viewerProvider;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Notice value) {
        Viewer viewer = this.viewerProvider.any(sender);

        this.noticeService.create()
            .viewer(viewer)
            .staticNotice(value)
            .send();
    }
}
