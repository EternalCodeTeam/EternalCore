package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;

@Route(name = "alert", aliases = { "broadcast", "bc" })
@Permission("eternalcore.alert")
public class AlertCommand {

    private final NoticeService noticeService;

    @Inject
    public AlertCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(min = 2)
    @DescriptionDocs(description = "Sends alert to all players with specified notice type and messages", arguments = "<type> <message>")
    void execute(@Arg NoticeTextType type, @Joiner String text) {
        this.noticeService.create()
            .notice(type, translation -> translation.chat().alertMessageFormat())
            .placeholder("{BROADCAST}", text)
            .onlinePlayers()
            .send();
    }
}
