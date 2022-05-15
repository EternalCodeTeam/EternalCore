package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.PermissionExclude;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "ping")
@PermissionExclude("eternalcore.command.ping")
public class PingCommand {

    private final NoticeService noticeService;

    public PingCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(CommandSender sender, Audience audience, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        if (sender.equals(player)) {
            this.noticeService.notice()
                .message(messages -> messages.other().pingMessage())
                .placeholder("{PING}", String.valueOf(player.getPing()))
                .audience(audience)
                .send();

            return;
        }

        this.noticeService.notice()
            .message(messages -> messages.other().pingOtherMessage())
            .placeholder("{PING}", String.valueOf(player.getPing()))
            .placeholder("{PLAYER}", player.getName())
            .audience(audience)
            .send();
    }
}
