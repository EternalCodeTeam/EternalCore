package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;

@Section(route = "msg", aliases = {"message", "whisper"})
@Permission("eternalcore.commands.message")
@UsageMessage("&8» &cPoprawne użycie &7/msg <player> <message>")
public final class MessageCommand {

    private final NoticeService noticeService;

    public MessageCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @MinArgs(2)
    public void execute(Player sender, @Arg(0) Player target, @Joiner String message) {

        this.noticeService
            .notice()
            .player(sender.getUniqueId())
            .message(messages -> messages.other().privateMessageSendFormat())
            .placeholder("{MESSAGE}", message)
            .placeholder("{TARGET}", target.getName())
            .send();
        this.noticeService
            .notice()
            .player(target.getUniqueId())
            .message(messages -> messages.other().privateMessageReceiveFormat())
            .placeholder("{MESSAGE}", message)
            .placeholder("{SENDER}", sender.getName())
            .send();

    }
}
