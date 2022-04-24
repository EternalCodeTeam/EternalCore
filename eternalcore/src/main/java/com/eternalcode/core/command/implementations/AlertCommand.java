package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;

@Section(route = "alert", aliases = { "broadcast", "bc" })
@Permission("eternalcore.command.alert")
@UsageMessage("&8» &cPoprawne użycie &7/alert <title/actionbar/chat> <text>")
public class AlertCommand {

    private final NoticeService noticeService;

    public AlertCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @MinArgs(2)
    public void execute(@Arg(0) NoticeType type, @Joiner String text) {
        this.noticeService.notice()
            .notice(type, messages -> messages.other().alertMessagePrefix())
            .placeholder("{BROADCAST}", text)
            .all()
            .send();
    }
}
