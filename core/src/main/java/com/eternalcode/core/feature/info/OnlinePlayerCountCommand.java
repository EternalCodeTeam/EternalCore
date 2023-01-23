package com.eternalcode.core.feature.info;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
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
            .notice(translation -> translation.player().onlineMessage())
            .viewer(viewer)
            .placeholder("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))
            .send();
    }
}
