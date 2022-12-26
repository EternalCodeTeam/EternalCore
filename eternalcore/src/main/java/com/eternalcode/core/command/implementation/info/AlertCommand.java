package com.eternalcode.core.command.implementation.info;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;

@Route(name = "alert", aliases = { "broadcast", "bc" })
@Permission("eternalcore.alert")
public class AlertCommand {

    private final NoticeService noticeService;

    public AlertCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(min = 2)
    void execute(@Arg NoticeType type, @Joiner String text) {
        this.noticeService.create()
            .notice(type, messages -> messages.other().alertMessagePrefix())
            .placeholder("{BROADCAST}", text)
            .onlinePlayers()
            .send();
    }
}
