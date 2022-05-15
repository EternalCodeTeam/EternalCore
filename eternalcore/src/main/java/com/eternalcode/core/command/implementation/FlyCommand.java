package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "fly")
@Permission("eternalcore.command.fly")
public class FlyCommand {

    private final NoticeService noticeService;

    public FlyCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Audience audience, CommandSender sender, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        this.noticeService.notice()
            .message(messages -> messages.other().flyMessage())
            .placeholder("{STATE}", messages -> player.getAllowFlight() ? messages.format().formatEnable() : messages.format().formatDisable())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.notice()
            .message(messages -> messages.other().flySetMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{STATE}", messages -> player.getAllowFlight() ? messages.format().formatEnable() : messages.format().formatDisable())
            .audience(audience)
            .send();
    }
}
