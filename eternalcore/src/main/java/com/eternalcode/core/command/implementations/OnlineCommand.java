package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;


@Section(route = "online")
@Permission("eternalcore.command.online")
public class OnlineCommand {

    private final NoticeService noticeService;
    private final Server server;

    public OnlineCommand(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    public void execute(Audience audience) {
        this.noticeService
            .notice()
            .message(messages -> messages.other().onlineMessage())
            .audience(audience)
            .placeholder("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))
            .send();
    }
}
