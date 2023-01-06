package com.eternalcode.core.command.implementation.info;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Server;


@Route(name = "online")
@Permission("eternalcore.online")
public class OnlinePlayerCountCommand {

    private final NoticeService noticeService;
    private final Server server;

    public OnlinePlayerCountCommand(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    void execute(Viewer viewer) {
        this.noticeService
            .create()
            .notice(messages -> messages.player().onlineMessage())
            .viewer(viewer)
            .placeholder("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))
            .send();
    }
}
