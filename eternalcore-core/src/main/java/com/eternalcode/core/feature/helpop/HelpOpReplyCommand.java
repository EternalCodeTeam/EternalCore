package com.eternalcode.core.feature.helpop;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.helpop.event.HelpOpReplyEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

@Command(name = "helpopreply")
@Permission("eternalcore.helpop.reply")
@PermissionDocs(
    name = "HelpOp Reply",
    description = "Allows administrator to reply to a player's helpop request",
    permission = "eternalcore.helpop.reply"
)
class HelpOpReplyCommand {

    private final NoticeService noticeService;
    private final HelpOpService helpOpService;
    private final EventCaller eventCaller;

    @Inject
    HelpOpReplyCommand(NoticeService noticeService, HelpOpService helpOpService, EventCaller eventCaller) {
        this.noticeService = noticeService;
        this.helpOpService = helpOpService;
        this.eventCaller = eventCaller;
    }

    @Execute
    @DescriptionDocs(description = "Reply to a player's helpop request", arguments = "<player> <message>")
    void execute(@Sender Player admin, @Arg Player target, @Join String message) {
        if (!this.helpOpService.hasSentHelpOp(target.getUniqueId())) {
            this.noticeService.create()
                .player(admin.getUniqueId())
                .notice(translation -> translation.helpOp().playerNotSentHelpOp())
                .placeholder("{PLAYER}", target.getName())
                .send();

            return;
        }

        HelpOpReplyEvent event = new HelpOpReplyEvent(admin, target, message);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        String escapedMessage = MiniMessage.miniMessage().escapeTags(event.getContent());

        this.noticeService.create()
            .console()
            .player(target.getUniqueId())
            .onlinePlayers(HelpOpCommand.HELPOP_SPY)
            .notice(translation -> translation.helpOp().adminReply())
            .placeholder("{ADMIN}", admin.getName())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{TEXT}", escapedMessage)
            .send();

        this.noticeService.create()
            .player(admin.getUniqueId())
            .notice(translation -> translation.helpOp().adminReplySend())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }
}
