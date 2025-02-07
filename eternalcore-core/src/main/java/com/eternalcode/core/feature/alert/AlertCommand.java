package com.eternalcode.core.feature.alert;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;

@Command(name = "alert", aliases = { "broadcast", "bc" })
@Permission("eternalcore.alert")
class AlertCommand {

    private final NoticeService noticeService;

    @Inject
    AlertCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Sends alert to all players with specified notice type and messages", arguments = "<type> <message>")
    void execute(@Arg NoticeTextType type, @Join String text) {
        this.noticeService.create()
            .notice(type, translation -> translation.chat().alertMessageFormat())
            .placeholder("{BROADCAST}", text)
            .onlinePlayers()
            .send();
    }
}
