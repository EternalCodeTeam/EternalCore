package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "ping")
public class PingCommand {

    private final NoticeService noticeService;

    public PingCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(CommandSender sender, Viewer audience, @Arg @By("or_sender") Player player) {
        if (sender.equals(player)) {
            this.noticeService.notice()
                .message(messages -> messages.other().pingMessage())
                .placeholder("{PING}", String.valueOf(player.getPing()))
                .viewer(audience)
                .send();

            return;
        }

        this.noticeService.notice()
            .message(messages -> messages.other().pingOtherMessage())
            .placeholder("{PING}", String.valueOf(player.getPing()))
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }
}
