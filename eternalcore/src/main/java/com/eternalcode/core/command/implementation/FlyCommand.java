package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "fly")
@Permission("eternalcore.fly")
public class FlyCommand {

    private final NoticeService noticeService;

    public FlyCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Viewer audience, CommandSender sender, @Arg @By("or_sender") Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        this.noticeService.create()
            .message(messages -> messages.other().flyMessage())
            .placeholder("{STATE}", messages -> player.getAllowFlight() ? messages.format().enable() : messages.format().disable())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .message(messages -> messages.other().flySetMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{STATE}", messages -> player.getAllowFlight() ? messages.format().enable() : messages.format().disable())
            .viewer(audience)
            .send();
    }
}
