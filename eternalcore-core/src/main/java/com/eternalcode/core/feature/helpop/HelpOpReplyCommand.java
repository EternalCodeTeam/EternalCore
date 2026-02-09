package com.eternalcode.core.feature.helpop;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.helpop.event.HelpOpReplyEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
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
    private final Server server;

    @Inject
    HelpOpReplyCommand(NoticeService noticeService, HelpOpService helpOpService, EventCaller eventCaller, Server server) {
        this.noticeService = noticeService;
        this.helpOpService = helpOpService;
        this.eventCaller = eventCaller;
        this.server = server;
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

        NoticeBroadcast notice = this.noticeService.create()
            .console()
            .player(target.getUniqueId())
            .notice(translation -> translation.helpOp().adminReply())
            .placeholder("{ADMIN}", admin.getName())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{TEXT}", escapedMessage);

        for (Player onlinePlayer : this.server.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission(HelpOpCommand.HELPOP_SPY)) {
                continue;
            }

            notice = notice.player(onlinePlayer.getUniqueId());
        }

        notice.send();

        this.noticeService.create()
            .player(admin.getUniqueId())
            .notice(translation -> translation.helpOp().adminReplySend())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }
}
