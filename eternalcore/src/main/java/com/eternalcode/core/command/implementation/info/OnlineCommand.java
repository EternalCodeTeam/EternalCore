package com.eternalcode.core.command.implementation.info;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Server;


@Section(route = "online")
@Permission("eternalcore.online")
public class OnlineCommand {

    private final NoticeService noticeService;
    private final Server server;

    public OnlineCommand(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    public void execute(Viewer audience) {
        this.noticeService
            .notice()
            .message(messages -> messages.other().onlineMessage())
            .viewer(audience)
            .placeholder("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))
            .send();
    }
}
