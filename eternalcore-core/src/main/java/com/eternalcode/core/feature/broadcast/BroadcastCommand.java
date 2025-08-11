package com.eternalcode.core.feature.broadcast;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;

import java.util.function.Function;

@Command(name = "broadcast", aliases = "bc")
@Permission("eternalcore.broadcast")
class BroadcastCommand {

    private final NoticeService noticeService;

    @Inject
    BroadcastCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Sends message broadcast to all players", arguments = "<message>")
    void executeChat(@Join String text) {
        this.sendBroadcast(NoticeTextType.CHAT, text, false);
    }

    @Execute
    @DescriptionDocs(description = "Sends message broadcast to all players (with plain method clearly message)", arguments = "<plain> <message>")
    void executeChatPlain(@Arg boolean plain, @Join String text) {
        this.sendBroadcast(NoticeTextType.CHAT, text, plain);
    }

    @Execute
    @DescriptionDocs(description = "Sends broadcast to all players with specified notice type and messages", arguments = "<type> <message>")
    void execute(@Arg NoticeTextType type, @Join String text) {
        this.sendBroadcast(type, text, false);
    }

    @Execute
    @DescriptionDocs(description = "Sends broadcast to all players with specified notice type and messages (with plain method clearly message)", arguments = "<plain> <type> <message>")
    void execute(@Arg boolean plain, @Arg NoticeTextType type, @Join String text) {
        this.sendBroadcast(type, text, plain);
    }

    void sendBroadcast(NoticeTextType type, String text, boolean plainText) {
        this.noticeService.create()
            .notice(type, translation ->  plainText
                ? text
                : translation.broadcast().messageFormat())
            .placeholder("{BROADCAST}", text)
            .onlinePlayers()
            .send();
    }
}
