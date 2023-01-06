package com.eternalcode.core.command.implementation;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.notification.NoticeType;
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
            .notice(type, translation -> translation.chat().alertMessageFormat())
            .placeholder("{BROADCAST}", text)
            .onlinePlayers()
            .send();
    }
}
