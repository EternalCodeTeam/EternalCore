package com.eternalcode.core.command.handler;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import org.bukkit.command.CommandSender;

public class NotificationHandler implements Handler<CommandSender, Notification> {

    private final BukkitViewerProvider viewerProvider;
    private final NoticeService noticeService;

    public NotificationHandler(BukkitViewerProvider viewerProvider, NoticeService noticeService) {
        this.viewerProvider = viewerProvider;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Notification value) {
        Viewer viewer = this.viewerProvider.sender(sender);

        this.noticeService.create()
            .viewer(viewer)
            .staticNotice(value)
            .send();
    }
}
