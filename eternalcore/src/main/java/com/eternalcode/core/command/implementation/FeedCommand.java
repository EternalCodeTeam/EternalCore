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

@Section(route = "feed")
@Permission("eternalcore.command.fly")
public class FeedCommand {

    private final NoticeService noticeService;

    public FeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Audience audience, CommandSender sender, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.setFoodLevel(20);

        this.noticeService.notice()
            .message(messages -> messages.other().foodMessage())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.notice()
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().foodOtherMessage())
            .audience(audience)
            .send();
    }
}

