package com.eternalcode.core.feature.essentials.playerinfo;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
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
    @DescriptionDocs(description = "Shows online players count")
    void execute(Viewer viewer) {
        this.noticeService
            .create()
            .notice(translation -> translation.player().onlinePlayersCountMessage())
            .viewer(viewer)
            .placeholder("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))
            .send();
    }
}
